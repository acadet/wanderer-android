package com.adriencadet.wanderer.models.bll.jobs;

import com.adriencadet.wanderer.models.bll.dto.PictureBLLDTO;
import com.adriencadet.wanderer.models.bll.dto.PlaceBLLDTO;
import com.adriencadet.wanderer.models.serializers.IPictureSerializer;
import com.adriencadet.wanderer.models.services.wanderer.IWandererServer;
import com.adriencadet.wanderer.models.services.wanderer.dto.PictureWandererServerDTO;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * ListPicturesForPlaceJob
 * <p>
 */
public class ListPicturesForPlaceJob extends BLLJob {
    private Observable<List<PictureBLLDTO>> observable;
    private PlaceBLLDTO                     place;

    ListPicturesForPlaceJob(IWandererServer server, IPictureSerializer serializer) {
        observable = Observable
            .create(new Observable.OnSubscribe<List<PictureBLLDTO>>() {
                @Override
                public void call(Subscriber<? super List<PictureBLLDTO>> subscriber) {
                    server
                        .listPicturesForPlaceJob(place.getId())
                        .observeOn(Schedulers.newThread())
                        .subscribe(new Observer<List<PictureWandererServerDTO>>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                handleError(e, subscriber);
                            }

                            @Override
                            public void onNext(List<PictureWandererServerDTO> pictureWandererServerDTOs) {
                                subscriber.onNext(serializer.serialize(pictureWandererServerDTOs));
                            }
                        });
                }
            })
            .subscribeOn(Schedulers.newThread());
    }

    public Observable<List<PictureBLLDTO>> create(PlaceBLLDTO place) {
        this.place = place;
        return observable;
    }
}
