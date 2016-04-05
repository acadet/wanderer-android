package com.adriencadet.wanderer.models.bll.jobs;

import com.adriencadet.wanderer.models.bll.dto.PlaceBLLDTO;
import com.adriencadet.wanderer.models.dao.IPlaceDAO;
import com.adriencadet.wanderer.models.serializers.IPlaceSerializer;
import com.adriencadet.wanderer.models.services.ServiceErrors;
import com.adriencadet.wanderer.models.services.wanderer.IWandererServer;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * ToggleLikeJob
 * <p>
 */
public class ToggleLikeJob extends BLLJob {
    private IWandererServer  server;
    private IPlaceSerializer placeSerializer;
    private IPlaceDAO        placeDAO;

    private Queue<Integer> pendingTransactions;

    ToggleLikeJob(IWandererServer server, IPlaceSerializer placeSerializer, IPlaceDAO placeDAO) {
        this.server = server;
        this.placeSerializer = placeSerializer;
        this.placeDAO = placeDAO;
        this.pendingTransactions = new ConcurrentLinkedQueue<>();
    }

    public Observable<PlaceBLLDTO> create(PlaceBLLDTO place) {
        return Observable
            .create(new Observable.OnSubscribe<PlaceBLLDTO>() {
                @Override
                public void call(Subscriber<? super PlaceBLLDTO> subscriber) {
                    Action1<Integer> pushToServer = (id) -> {
                        server
                            .toggleLikeJob(id)
                            .observeOn(Schedulers.newThread())
                            .subscribe(new Observer<Void>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    if (e instanceof ServiceErrors.NoConnection) {
                                        pendingTransactions.add(id);
                                    } else {
                                        Timber.e(e, "Failed to toggle like");
                                    }
                                }

                                @Override
                                public void onNext(Void aVoid) {

                                }
                            });
                    };

                    PlaceBLLDTO updatedPlace = placeSerializer.fromDAO(placeDAO.toggleLike(place.getId()));

                    subscriber.onNext(updatedPlace);
                    subscriber.onCompleted();

                    while (!pendingTransactions.isEmpty()) {
                        pushToServer.call(pendingTransactions.poll());
                    }

                    pushToServer.call(place.getId());
                }
            })
            .subscribeOn(Schedulers.newThread());
    }
}
