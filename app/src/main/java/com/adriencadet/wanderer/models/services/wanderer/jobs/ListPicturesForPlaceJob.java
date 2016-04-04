package com.adriencadet.wanderer.models.services.wanderer.jobs;

import com.adriencadet.wanderer.models.services.RetrofitJob;
import com.adriencadet.wanderer.models.services.wanderer.api.IWandererAPI;
import com.adriencadet.wanderer.models.services.wanderer.dto.PictureWandererServerDTO;

import java.util.List;

import retrofit.RetrofitError;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * ListPicturesForPlaceJob
 * <p>
 */
public class ListPicturesForPlaceJob extends RetrofitJob {
    private IWandererAPI api;

    ListPicturesForPlaceJob(IWandererAPI api) {
        this.api = api;
    }

    public Observable<List<PictureWandererServerDTO>> create(int placeID) {
        return Observable
            .create(new Observable.OnSubscribe<List<PictureWandererServerDTO>>() {
                @Override
                public void call(Subscriber<? super List<PictureWandererServerDTO>> subscriber) {
                    try {
                        List<PictureWandererServerDTO> outcome = api.listPicturesForPlace(placeID);

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
