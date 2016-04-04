package com.adriencadet.wanderer.models.services.wanderer.jobs;

import com.adriencadet.wanderer.models.services.RetrofitJob;
import com.adriencadet.wanderer.models.services.wanderer.api.IWandererAPI;

import retrofit.RetrofitError;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * ToggleLikeJob
 * <p>
 */
public class ToggleLikeJob extends RetrofitJob {
    private Observable<Void> observable;
    private int              placeID;

    ToggleLikeJob(IWandererAPI api) {
        observable = Observable
            .create(new Observable.OnSubscribe<Void>() {
                @Override
                public void call(Subscriber<? super Void> subscriber) {
                    try {
                        api.toggleLike(placeID);

                        subscriber.onNext(null);
                        subscriber.onCompleted();
                    } catch (RetrofitError e) {
                        handleError(e, subscriber);
                    }
                }
            })
            .subscribeOn(Schedulers.newThread());
    }

    public Observable<Void> create(int placeID) {
        this.placeID = placeID;

        return observable;
    }
}
