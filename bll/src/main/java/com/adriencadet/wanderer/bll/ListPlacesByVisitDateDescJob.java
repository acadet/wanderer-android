package com.adriencadet.wanderer.bll;

import com.adriencadet.wanderer.beans.Picture;
import com.adriencadet.wanderer.beans.Place;
import com.adriencadet.wanderer.dao.IPictureDAO;
import com.adriencadet.wanderer.dao.IPlaceDAO;
import com.adriencadet.wanderer.services.wanderer.IWandererServer;
import com.annimon.stream.Stream;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * ListPlacesByVisitDateDescJob
 * <p>
 */
public class ListPlacesByVisitDateDescJob extends BLLJob {
    private static final Object latestFetchLock = new Object();

    private DateTime     latestFetch;
    private Subscription listPlacesByVisitDateDescJobSubscription;

    private Configuration   configuration;
    private IWandererServer wandererServer;
    private IPlaceDAO       placeDAO;
    private IPictureDAO     pictureDAO;

    ListPlacesByVisitDateDescJob(
        Configuration configuration,
        IWandererServer wandererServer,
        IPlaceDAO placeDAO, IPictureDAO pictureDAO) {

        this.configuration = configuration;
        this.wandererServer = wandererServer;
        this.placeDAO = placeDAO;
        this.pictureDAO = pictureDAO;
    }

    private void updateCache(List<Place> places) {
        DateTime fetchDate = DateTime.now();

        if (latestFetch != null && latestFetch.isAfter(fetchDate)) {
            return;
        }

        synchronized (latestFetchLock) {
            if (latestFetch != null && latestFetch.isAfter(fetchDate)) {
                return;
            }

            latestFetch = fetchDate;
            placeDAO.save(places);
            Stream
                .of(places)
                .forEach((e) -> pictureDAO.save(e, e.getMainPicture()));
        }
    }

    private boolean tryToUseCache(Subscriber<? super List<Place>> subscriber) {
        List<Place> cachedList = placeDAO.listPlacesByVisitDateDescJob();
        boolean wasInterrupted = false;

        for (Place p : cachedList) {
            Picture pic = pictureDAO.find(p.getMainPicture().getId());

            if (pic != null) {
                p.getMainPicture().setUrl(pic.getUrl());
            } else {
                // Missing picture in cache, server has to be fetched
                wasInterrupted = true;
                break;
            }
        }

        if (!wasInterrupted) {
            subscriber.onNext(cachedList);
            subscriber.onCompleted();
            return true;
        } else {
            return false;
        }
    }

    private void useCache(Subscriber<? super List<Place>> subscriber) {
        List<Place> cachedList = placeDAO.listPlacesByVisitDateDescJob();
        List<Place> outcome = new ArrayList<>();

        for (Place p : cachedList) {
            Picture pic = pictureDAO.find(p.getMainPicture().getId());

            if (pic != null) {
                p.getMainPicture().setUrl(pic.getUrl());
                outcome.add(p);
            }
        }

        subscriber.onNext(outcome);
        subscriber.onCompleted();
    }

    public Observable<List<Place>> create() {
        return Observable
            .create(new Observable.OnSubscribe<List<Place>>() {
                @Override
                public void call(Subscriber<? super List<Place>> subscriber) {
                    FinalWrapper<List<Place>> list;

                    if (latestFetch != null
                        && latestFetch.plusMinutes(configuration.PLACE_CACHING_DURATION_MINS).isAfterNow()) {
                        if (tryToUseCache(subscriber)) {
                            return;
                        }
                    }

                    list = new FinalWrapper<>();

                    if (listPlacesByVisitDateDescJobSubscription != null) {
                        listPlacesByVisitDateDescJobSubscription.unsubscribe();
                    }

                    listPlacesByVisitDateDescJobSubscription = wandererServer
                        .listPlacesByVisitDateDescJob()
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Observer<List<Place>>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                                updateCache(list.get());
                            }

                            @Override
                            public void onError(Throwable e) {
                                useCache(subscriber);
                                handleError(e, subscriber);
                            }

                            @Override
                            public void onNext(List<Place> places) {
                                list.set(places);

                                subscriber.onNext(list.get());
                            }
                        });
                }
            })
            .subscribeOn(Schedulers.newThread());
    }
}
