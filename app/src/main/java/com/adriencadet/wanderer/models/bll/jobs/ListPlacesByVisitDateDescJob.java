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
import com.adriencadet.wanderer.models.structs.FinalWrapper;
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
    private static final Object latestFetchLock = new Object();

    private DateTime                 latestFetch;
    private ApplicationConfiguration configuration;
    private IWandererServer          wandererServer;
    private IPlaceSerializer         placeSerializer;
    private IPictureSerializer       pictureSerializer;
    private IPlaceDAO                placeDAO;
    private IPictureDAO              pictureDAO;

    ListPlacesByVisitDateDescJob(
        ApplicationConfiguration configuration,
        IWandererServer wandererServer,
        IPlaceSerializer placeSerializer, IPictureSerializer pictureSerializer,
        IPlaceDAO placeDAO, IPictureDAO pictureDAO) {

        this.configuration = configuration;
        this.wandererServer = wandererServer;
        this.placeSerializer = placeSerializer;
        this.pictureSerializer = pictureSerializer;
        this.placeDAO = placeDAO;
        this.pictureDAO = pictureDAO;
    }

    private void updateCache(List<PlaceBLLDTO> places) {
        DateTime fetchDate = DateTime.now();

        if (latestFetch != null && latestFetch.isAfter(fetchDate)) {
            return;
        }

        synchronized (latestFetchLock) {
            if (latestFetch != null && latestFetch.isAfter(fetchDate)) {
                return;
            }

            latestFetch = fetchDate;
            placeDAO.save(placeSerializer.toDAO(places));
            Stream
                .of(places)
                .forEach((e) -> pictureDAO.save(pictureSerializer.toDAO(e.getMainPicture())));
        }
    }

    public Observable<List<PlaceBLLDTO>> create() {
        return Observable
            .create(new Observable.OnSubscribe<List<PlaceBLLDTO>>() {
                @Override
                public void call(Subscriber<? super List<PlaceBLLDTO>> subscriber) {
                    if (latestFetch != null
                        && latestFetch.plusMinutes(configuration.PLACE_CACHING_DURATION_MINS).isAfterNow()) {

                        List<PlaceBLLDTO> list = placeSerializer.fromDAO(placeDAO.listPlacesByVisitDateDescJob());
                        boolean wasInterrupted = false;

                        for (PlaceBLLDTO p : list) {
                            PictureDAODTO pic = pictureDAO.find(p.getMainPicture().getId());

                            if (pic != null) {
                                p.getMainPicture().setUrl(pic.getUrl());
                            } else {
                                // Missing picture in cache, server has to be fetched
                                wasInterrupted = true;
                                break;
                            }
                        }

                        if (!wasInterrupted) {
                            subscriber.onNext(list);
                            subscriber.onCompleted();
                            return;
                        }
                    }

                    FinalWrapper<List<PlaceBLLDTO>> list = new FinalWrapper<>();

                    wandererServer
                        .listPlacesByVisitDateDescJob()
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Observer<List<PlaceWandererServerDTO>>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();

                                updateCache(list.get());
                            }

                            @Override
                            public void onError(Throwable e) {
                                handleError(e, subscriber);
                            }

                            @Override
                            public void onNext(List<PlaceWandererServerDTO> placeWandererServerDTOs) {
                                list.set(placeSerializer.fromWandererServer(placeWandererServerDTOs));

                                subscriber.onNext(list.get());
                            }
                        });
                }
            })
            .subscribeOn(Schedulers.newThread());
    }
}
