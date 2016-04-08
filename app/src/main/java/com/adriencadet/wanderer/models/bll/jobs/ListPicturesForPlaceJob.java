package com.adriencadet.wanderer.models.bll.jobs;

import com.adriencadet.wanderer.beans.beans.Picture;
import com.adriencadet.wanderer.beans.beans.Place;
import com.adriencadet.wanderer.ApplicationConfiguration;
import com.adriencadet.wanderer.dao.IPictureDAO;
import com.adriencadet.wanderer.dao.dto.PictureDAODTO;
import com.adriencadet.wanderer.models.serializers.IPictureSerializer;
import com.adriencadet.wanderer.services.wanderer.IWandererServer;
import com.adriencadet.wanderer.services.wanderer.PictureWandererServerDTO;
import com.adriencadet.wanderer.models.structs.FinalWrapper;
import com.annimon.stream.Stream;

import org.joda.time.DateTime;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * ListPicturesForPlaceJob
 * <p>
 */
public class ListPicturesForPlaceJob extends BLLJob {
    private ApplicationConfiguration                 configuration;
    private IWandererServer                          server;
    private IPictureSerializer                       serializer;
    private IPictureDAO                              pictureDAO;
    private ConcurrentHashMap<Integer, DateTime>     latestFetchMap;
    private ConcurrentHashMap<Integer, Subscription> listPicturesForPlaceJobSubscriptionMap;

    ListPicturesForPlaceJob(
        ApplicationConfiguration configuration, IWandererServer server, IPictureSerializer serializer, IPictureDAO pictureDAO) {
        this.configuration = configuration;
        this.server = server;
        this.serializer = serializer;
        this.pictureDAO = pictureDAO;
        this.latestFetchMap = new ConcurrentHashMap<>();
        this.listPicturesForPlaceJobSubscriptionMap = new ConcurrentHashMap<>();
    }

    private void updateCache(Place place, List<Picture> pictures) {
        int key = place.getId();
        DateTime fetchDate = DateTime.now();
        List<PictureDAODTO> daos;
        DateTime latestFetch;

        if (!latestFetchMap.containsKey(key)) {
            return;
        }

        latestFetch = latestFetchMap.get(key);

        if (latestFetch.isAfter(fetchDate)) {
            return;
        }

        latestFetchMap.replace(place.getId(), latestFetch, fetchDate);
        daos = serializer.toDAO(pictures);
        Stream.of(daos).forEach((e) -> e.setPlaceID(place.getId()));
        pictureDAO.save(daos);
    }

    private void useCache(Place place, Subscriber<? super List<Picture>> subscriber) {
        subscriber.onNext(serializer.fromDAO(pictureDAO.listForPlace(place.getId())));
        subscriber.onCompleted();
    }

    public Observable<List<Picture>> create(Place place) {
        return Observable
            .create(new Observable.OnSubscribe<List<Picture>>() {
                @Override
                public void call(Subscriber<? super List<Picture>> subscriber) {
                    if (!latestFetchMap.containsKey(place.getId())
                        || latestFetchMap.get(place.getId()).plusMinutes(configuration.PICTURE_CACHING_DURATION_MINS).isBeforeNow()) {
                        final FinalWrapper<List<Picture>> list = new FinalWrapper<>();
                        Subscription subscription;
                        int key = place.getId();

                        subscription = server
                            .listPicturesForPlaceJob(place.getId())
                            .observeOn(Schedulers.newThread())
                            .subscribe(new Observer<List<PictureWandererServerDTO>>() {
                                @Override
                                public void onCompleted() {
                                    subscriber.onCompleted();

                                    updateCache(place, list.get());
                                }

                                @Override
                                public void onError(Throwable e) {
                                    useCache(place, subscriber);
                                    handleError(e, subscriber);
                                }

                                @Override
                                public void onNext(List<PictureWandererServerDTO> pictureWandererServerDTOs) {
                                    list.set(serializer.fromWandererServer(pictureWandererServerDTOs));
                                    subscriber.onNext(list.get());
                                }
                            });

                        if (listPicturesForPlaceJobSubscriptionMap.containsKey(key)) {
                            Subscription formerSubscription = listPicturesForPlaceJobSubscriptionMap.get(key);
                            formerSubscription.unsubscribe();
                            listPicturesForPlaceJobSubscriptionMap.replace(key, formerSubscription, subscription);
                        }
                    } else {
                        useCache(place, subscriber);
                    }
                }
            })
            .subscribeOn(Schedulers.newThread());
    }
}
