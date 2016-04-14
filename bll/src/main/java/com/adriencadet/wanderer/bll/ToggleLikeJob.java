package com.adriencadet.wanderer.bll;

import com.adriencadet.wanderer.beans.Place;
import com.adriencadet.wanderer.dao.IPlaceDAO;
import com.adriencadet.wanderer.services.wanderer.IWandererServer;

import java.util.concurrent.ConcurrentLinkedQueue;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * ToggleLikeJob
 * <p>
 */
public class ToggleLikeJob extends BLLJob {
    private IWandererServer server;
    private IPlaceDAO       placeDAO;

    private ConcurrentLinkedQueue<Integer> pendingTransactions;

    ToggleLikeJob(IWandererServer server, IPlaceDAO placeDAO) {
        this.server = server;
        this.placeDAO = placeDAO;
        this.pendingTransactions = new ConcurrentLinkedQueue<>();
    }

    private void pushToServer() {
        int id;

        if (pendingTransactions.isEmpty()) {
            return;
        }

        id = pendingTransactions.peek();
        server
            .toggleLikeJob(id)
            .observeOn(Schedulers.newThread())
            .subscribe(new Observer<Void>() {
                @Override
                public void onCompleted() {
                    pendingTransactions.poll();
                    pushToServer(); // Keep iterating on the queue
                }

                @Override
                public void onError(Throwable e) {
                    // Stop and wait for next toggling
                    Timber.e(e, "Failed to toggle like");
                }

                @Override
                public void onNext(Void aVoid) {

                }
            });
    }

    public Observable<Place> create(Place place) {
        return Observable
            .create(new Observable.OnSubscribe<Place>() {
                @Override
                public void call(Subscriber<? super Place> subscriber) {
                    Place updatedPlace = placeDAO.toggleLike(place);

                    subscriber.onNext(updatedPlace);
                    subscriber.onCompleted();

                    pendingTransactions.add(place.getId());
                    pushToServer();
                }
            })
            .subscribeOn(Schedulers.newThread());
    }
}
