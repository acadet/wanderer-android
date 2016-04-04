package com.adriencadet.wanderer.models.bll.jobs;

import com.adriencadet.wanderer.ApplicationConfiguration;
import com.adriencadet.wanderer.models.bll.dto.PlaceBLLDTO;
import com.adriencadet.wanderer.models.dao.IPictureDAO;
import com.adriencadet.wanderer.models.dao.IPlaceDAO;
import com.adriencadet.wanderer.models.dao.dto.PictureDAODTO;
import com.adriencadet.wanderer.models.serializers.IPictureSerializer;
import com.adriencadet.wanderer.models.serializers.IPlaceSerializer;
import com.adriencadet.wanderer.models.services.wanderer.IWandererServer;
import com.adriencadet.wanderer.models.services.wanderer.dto.PlaceWandererServerDTO;
import com.annimon.stream.Stream;

import org.joda.time.DateTime;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * ListPlacesByVisitDateDescJob
 * <p>
 */
public class ListPlacesByVisitDateDescJob extends BLLJob {
    private Observable<List<PlaceBLLDTO>> observable;
    private DateTime                      latestFetch;

    ListPlacesByVisitDateDescJob(
        ApplicationConfiguration configuration,
        IWandererServer wandererServer,
        IPlaceSerializer serializer, IPictureSerializer pictureSerializer,
        IPlaceDAO placeDAO, IPictureDAO pictureDAO) {

        // TODO: Generate observable by capturing the vars
        observable = Observable
            .create(new Observable.OnSubscribe<List<PlaceBLLDTO>>() {
                @Override
                public void call(Subscriber<? super List<PlaceBLLDTO>> subscriber) {
                    if (latestFetch != null
                        && latestFetch.plusMinutes(configuration.PLACE_CACHING_DURATION_MINS).isAfterNow()) {
                        List<PlaceBLLDTO> list = serializer.fromDAO(placeDAO.listPlacesByVisitDateDescJob());
                        boolean wasInterrupted = false;

                        for (PlaceBLLDTO p : list) {
                            PictureDAODTO pic = pictureDAO.find(p.getMainPicture().getId());

                            if (pic != null) {
                                p.getMainPicture().setUrl(pic.getUrl());
                            } else {
                                wasInterrupted = true;
                                break;
                            }
                        }

                        if (!wasInterrupted) {
                            subscriber.onNext(list);
                            subscriber.onCompleted();
                            return;
                        }
                        // Missing picture in cache, server has to be fetched
                    }

                    wandererServer
                        .listPlacesByVisitDateDescJob()
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Observer<List<PlaceWandererServerDTO>>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                                latestFetch = DateTime.now();
                            }

                            @Override
                            public void onError(Throwable e) {
                                handleError(e, subscriber);
                            }

                            @Override
                            public void onNext(List<PlaceWandererServerDTO> placeWandererServerDTOs) {
                                List<PlaceBLLDTO> list = serializer.fromWandererServer(placeWandererServerDTOs);

                                subscriber.onNext(list);

                                // TODO: ditto than pics
                                placeDAO.save(serializer.toDAO(list));
                                Stream
                                    .of(list)
                                    .forEach((e) -> pictureDAO.save(pictureSerializer.toDAO(e.getMainPicture())));
                            }
                        });
                }
            })
            .subscribeOn(Schedulers.newThread());
    }

    public Observable<List<PlaceBLLDTO>> create() {
        return observable;
    }
}
