package com.adriencadet.wanderer.bll;

import com.adriencadet.wanderer.dao.IPlaceDAO;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * CanUseRandomPlaceJob
 * <p>
 */
public class CanUseRandomPlaceJob {
    private IPlaceDAO placeDAO;

    CanUseRandomPlaceJob(IPlaceDAO placeDAO) {
        this.placeDAO = placeDAO;
    }

    public Observable<Boolean> create() {
        return Observable
            .create(new Observable.OnSubscribe<Boolean>() {
                @Override
                public void call(Subscriber<? super Boolean> subscriber) {
                    //TODO check pic as well
                    subscriber.onNext(placeDAO.hasEntries());
                    subscriber.onCompleted();
                }
            })
            .subscribeOn(Schedulers.newThread());
    }
}
