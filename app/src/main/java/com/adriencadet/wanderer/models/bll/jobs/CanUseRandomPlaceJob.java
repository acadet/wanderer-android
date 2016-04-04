package com.adriencadet.wanderer.models.bll.jobs;

import com.adriencadet.wanderer.models.dao.IPlaceDAO;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * CanUseRandomPlaceJob
 * <p>
 */
public class CanUseRandomPlaceJob {
    private Observable<Boolean> observable;

    CanUseRandomPlaceJob(IPlaceDAO placeDAO) {
        observable = Observable
            .create(new Observable.OnSubscribe<Boolean>() {
                @Override
                public void call(Subscriber<? super Boolean> subscriber) {
                    subscriber.onNext(placeDAO.hasEntries());
                    subscriber.onCompleted();
                }
            })
            .subscribeOn(Schedulers.newThread());
    }

    public Observable<Boolean> create() {
        return observable;
    }
}
