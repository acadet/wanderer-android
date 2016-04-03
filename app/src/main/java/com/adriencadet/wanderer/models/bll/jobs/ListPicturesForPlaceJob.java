package com.adriencadet.wanderer.models.bll.jobs;

import com.adriencadet.wanderer.ApplicationConfiguration;
import com.adriencadet.wanderer.models.bll.dto.PictureBLLDTO;
import com.adriencadet.wanderer.models.bll.dto.PlaceBLLDTO;
import com.adriencadet.wanderer.models.dao.IPictureDAO;
import com.adriencadet.wanderer.models.dao.dto.PictureDAODTO;
import com.adriencadet.wanderer.models.serializers.IPictureSerializer;
import com.adriencadet.wanderer.models.services.wanderer.IWandererServer;
import com.adriencadet.wanderer.models.services.wanderer.dto.PictureWandererServerDTO;
import com.annimon.stream.Stream;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * ListPicturesForPlaceJob
 * <p>
 */
public class ListPicturesForPlaceJob extends BLLJob {
    private Observable<List<PictureBLLDTO>> observable;
    private Map<Integer, DateTime>          latestFetches;
    private PlaceBLLDTO                     place;

    ListPicturesForPlaceJob(
        ApplicationConfiguration configuration, IWandererServer server, IPictureSerializer serializer, IPictureDAO pictureDAO) {

        latestFetches = new HashMap<>();

        observable = Observable
            .create(new Observable.OnSubscribe<List<PictureBLLDTO>>() {
                @Override
                public void call(Subscriber<? super List<PictureBLLDTO>> subscriber) {
                    if (!latestFetches.containsKey(place.getId())
                        || latestFetches.get(place.getId()).plusMinutes(configuration.PICTURE_CACHING_DURATION_MINS).isBeforeNow()) {
                        server
                            .listPicturesForPlaceJob(place.getId())
                            .observeOn(Schedulers.newThread())
                            .subscribe(new Observer<List<PictureWandererServerDTO>>() {
                                @Override
                                public void onCompleted() {
                                    subscriber.onCompleted();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    handleError(e, subscriber);
                                }

                                @Override
                                public void onNext(List<PictureWandererServerDTO> pictureWandererServerDTOs) {
                                    List<PictureBLLDTO> list = serializer.fromWandererServer(pictureWandererServerDTOs);
                                    List<PictureDAODTO> daos;

                                    subscriber.onNext(list);

                                    // TODO: moves computations to onCompleted
                                    // Maybe useful to have different instances of jobs instead of a singleton
                                    latestFetches.put(place.getId(), DateTime.now());
                                    daos = serializer.toDAO(list);
                                    Stream.of(daos).forEach((e) -> e.setPlaceID(place.getId()));
                                    pictureDAO.save(daos);
                                }
                            });
                    } else {
                        subscriber.onNext(serializer.fromDAO(pictureDAO.listForPlace(place.getId())));
                        subscriber.onCompleted();
                    }
                }
            })
            .subscribeOn(Schedulers.newThread());
    }

    public Observable<List<PictureBLLDTO>> create(PlaceBLLDTO place) {
        this.place = place;
        return observable;
    }
}
