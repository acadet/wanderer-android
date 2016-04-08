package com.adriencadet.wanderer.services.wanderer;

import com.adriencadet.wanderer.beans.Place;
import com.adriencadet.wanderer.services.RetrofitJob;

import java.util.List;

import retrofit.RetrofitError;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * ListPlacesByVisitDateDescJob
 * <p>
 */
class ListPlacesByVisitDateDescJob extends RetrofitJob {
    private IWandererAPI     api;
    private IPlaceSerializer placeSerializer;

    ListPlacesByVisitDateDescJob(IWandererAPI api, IPlaceSerializer placeSerializer) {
        this.api = api;
        this.placeSerializer = placeSerializer;
    }

    public Observable<List<Place>> create() {
        return Observable
            .create(new Observable.OnSubscribe<List<Place>>() {
                @Override
                public void call(Subscriber<? super List<Place>> subscriber) {
                    try {
                        List<PlaceDTO> fetch = api.listPlacesByVisitDateDesc();
                        List<Place> outcome = placeSerializer.fromWandererServer(fetch);

                        subscriber.onNext(outcome);
                        subscriber.onCompleted();
                    } catch (RetrofitError e) {
                        handleError(e, subscriber);
                    }
                }
            })
            .subscribeOn(Schedulers.newThread());
    }
}
