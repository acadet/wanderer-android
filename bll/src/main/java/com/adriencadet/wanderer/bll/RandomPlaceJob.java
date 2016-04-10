package com.adriencadet.wanderer.bll;

import com.adriencadet.wanderer.beans.Place;
import com.adriencadet.wanderer.dao.IPlaceDAO;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * RandomPlaceJob
 * <p>
 */
public class RandomPlaceJob {
    private IPlaceDAO placeDAO;

    RandomPlaceJob(IPlaceDAO placeDAO) {
        this.placeDAO = placeDAO;
    }

    public Observable<Place> create() {
        return Observable
            .create(new Observable.OnSubscribe<Place>() {
                @Override
                public void call(Subscriber<? super Place> subscriber) {
                    Place place = placeDAO.randomEntry();

                    subscriber.onNext(place);
                    subscriber.onCompleted();
                }
            })
            .subscribeOn(Schedulers.newThread());
    }
}
