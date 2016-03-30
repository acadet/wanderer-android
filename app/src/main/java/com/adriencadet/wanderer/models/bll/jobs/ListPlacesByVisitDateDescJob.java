package com.adriencadet.wanderer.models.bll.jobs;

import com.adriencadet.wanderer.models.bll.dto.PlaceBLLDTO;
import com.adriencadet.wanderer.models.serializers.IPlaceSerializer;
import com.adriencadet.wanderer.models.services.wanderer.IWandererServer;
import com.adriencadet.wanderer.models.services.wanderer.dto.PlaceWandererServerDTO;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * ListPlacesByVisitDateDescJob
 * <p>
 */
public class ListPlacesByVisitDateDescJob extends BLLJob {
    private Observable<List<PlaceBLLDTO>> observable;

    ListPlacesByVisitDateDescJob(IWandererServer wandererServer, IPlaceSerializer serializer) {
        observable = Observable
            .create(new Observable.OnSubscribe<List<PlaceBLLDTO>>() {
                @Override
                public void call(Subscriber<? super List<PlaceBLLDTO>> subscriber) {
                    wandererServer
                        .listPlacesByVisitDateDescJob()
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Observer<List<PlaceWandererServerDTO>>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                handleError(e, subscriber);
                            }

                            @Override
                            public void onNext(List<PlaceWandererServerDTO> placeWandererServerDTOs) {
                                subscriber.onNext(serializer.serialize(placeWandererServerDTOs));
                            }
                        });
                }
            })
            .subscribeOn(Schedulers.newThread());
    }

    public Observable<List<PlaceBLLDTO>> create() {
        return observable;
    }
}
