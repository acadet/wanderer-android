package com.adriencadet.wanderer.models.bll.jobs;

import com.adriencadet.wanderer.models.bll.dto.PlaceBLLDTO;
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
    private Observable<PlaceBLLDTO> observable;

    RandomPlaceJob(IPlaceSerializer placeSerializer, IPlaceDAO placeDAO) {
        observable = Observable
            .create(new Observable.OnSubscribe<PlaceBLLDTO>() {
                @Override
                public void call(Subscriber<? super PlaceBLLDTO> subscriber) {
                    PlaceBLLDTO place = placeSerializer.fromDAO(placeDAO.randomEntry());

                    subscriber.onNext(place);
                    subscriber.onCompleted();
                }
            })
            .subscribeOn(Schedulers.newThread());
    }

    public Observable<PlaceBLLDTO> create() {
        return observable;
    }
}
