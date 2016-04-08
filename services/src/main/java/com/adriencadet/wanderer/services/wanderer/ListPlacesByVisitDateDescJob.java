package com.adriencadet.wanderer.services.wanderer;

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
    private IWandererAPI api;

    ListPlacesByVisitDateDescJob(IWandererAPI api) {
        this.api = api;
    }

    public Observable<List<PlaceDTO>> create() {
        return Observable
            .create(new Observable.OnSubscribe<List<PlaceDTO>>() {
                @Override
                public void call(Subscriber<? super List<PlaceDTO>> subscriber) {
                    try {
                        List<PlaceDTO> outcome = api.listPlacesByVisitDateDesc();

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
