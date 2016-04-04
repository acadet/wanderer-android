package com.adriencadet.wanderer.models.services.wanderer.jobs;

import com.adriencadet.wanderer.models.services.RetrofitJob;
import com.adriencadet.wanderer.models.services.wanderer.api.IWandererAPI;
import com.adriencadet.wanderer.models.services.wanderer.dto.PlaceWandererServerDTO;

import java.util.List;

import retrofit.RetrofitError;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * ListPlacesByVisitDateDescJob
 * <p>
 */
public class ListPlacesByVisitDateDescJob extends RetrofitJob {
    private IWandererAPI api;

    ListPlacesByVisitDateDescJob(IWandererAPI api) {
        this.api = api;
    }

    public Observable<List<PlaceWandererServerDTO>> create() {
        return Observable
            .create(new Observable.OnSubscribe<List<PlaceWandererServerDTO>>() {
                @Override
                public void call(Subscriber<? super List<PlaceWandererServerDTO>> subscriber) {
                    try {
                        List<PlaceWandererServerDTO> outcome = api.listPlacesByVisitDateDesc();

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
