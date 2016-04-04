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
    private IPlaceSerializer placeSerializer;
    private IPlaceDAO        placeDAO;

    RandomPlaceJob(IPlaceSerializer placeSerializer, IPlaceDAO placeDAO) {
        this.placeSerializer = placeSerializer;
        this.placeDAO = placeDAO;
    }

    public Observable<PlaceBLLDTO> create() {
        return Observable
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
}
