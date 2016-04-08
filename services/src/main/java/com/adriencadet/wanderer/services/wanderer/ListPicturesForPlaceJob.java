package com.adriencadet.wanderer.services.wanderer;

import com.adriencadet.wanderer.services.RetrofitJob;

import java.util.List;

import retrofit.RetrofitError;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * ListPicturesForPlaceJob
 * <p>
 */
class ListPicturesForPlaceJob extends RetrofitJob {
    private IWandererAPI api;

    ListPicturesForPlaceJob(IWandererAPI api) {
        this.api = api;
    }

    public Observable<List<PictureDTO>> create(int placeID) {
        return Observable
            .create(new Observable.OnSubscribe<List<PictureDTO>>() {
                @Override
                public void call(Subscriber<? super List<PictureDTO>> subscriber) {
                    try {
                        List<PictureDTO> outcome = api.listPicturesForPlace(placeID);

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
