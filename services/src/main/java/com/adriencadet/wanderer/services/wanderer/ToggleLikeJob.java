package com.adriencadet.wanderer.services.wanderer;

import com.adriencadet.wanderer.services.RetrofitJob;

import retrofit.RetrofitError;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * ToggleLikeJob
 * <p>
 */
class ToggleLikeJob extends RetrofitJob {
    private IWandererAPI api;

    ToggleLikeJob(IWandererAPI api) {
        this.api = api;
    }

    public Observable<Void> create(int placeID) {
        return Observable
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
}
