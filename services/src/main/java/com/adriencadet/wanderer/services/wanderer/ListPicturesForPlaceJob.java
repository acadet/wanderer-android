package com.adriencadet.wanderer.services.wanderer;

import com.adriencadet.wanderer.beans.Picture;
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
    private IWandererAPI       api;
    private IPictureSerializer pictureSerializer;

    ListPicturesForPlaceJob(IWandererAPI api, IPictureSerializer pictureSerializer) {
        this.api = api;
        this.pictureSerializer = pictureSerializer;
    }

    public Observable<List<Picture>> create(int placeID) {
        return Observable
            .create(new Observable.OnSubscribe<List<Picture>>() {
                @Override
                public void call(Subscriber<? super List<Picture>> subscriber) {
                    try {
                        List<PictureDTO> fetch = api.listPicturesForPlace(placeID);
                        List<Picture> outcome = pictureSerializer.fromWandererServer(fetch);

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
