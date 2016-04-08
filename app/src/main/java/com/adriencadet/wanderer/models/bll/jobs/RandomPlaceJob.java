package com.adriencadet.wanderer.models.bll.jobs;

import com.adriencadet.beans.Place;
import com.adriencadet.wanderer.models.dao.IPlaceDAO;
import com.adriencadet.wanderer.models.serializers.IPlaceSerializer;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * RandomPlaceJob
 * <p>
 */
public class RandomPlaceJob {
    private IPlaceSerializer placeSerializer;
    private IPlaceDAO        placeDAO;

    RandomPlaceJob(IPlaceSerializer placeSerializer, IPlaceDAO placeDAO) {
        this.placeSerializer = placeSerializer;
        this.placeDAO = placeDAO;
    }

    public Observable<Place> create() {
        return Observable
            .create(new Observable.OnSubscribe<Place>() {
                @Override
                public void call(Subscriber<? super Place> subscriber) {
                    Place place = placeSerializer.fromDAO(placeDAO.randomEntry());

                    subscriber.onNext(place);
                    subscriber.onCompleted();
                }
            })
            .subscribeOn(Schedulers.newThread());
    }
}
