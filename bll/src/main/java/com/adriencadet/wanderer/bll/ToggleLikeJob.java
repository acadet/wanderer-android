package com.adriencadet.wanderer.bll;

import com.adriencadet.wanderer.beans.Picture;
import com.adriencadet.wanderer.beans.Place;
import com.adriencadet.wanderer.dao.IPictureDAO;
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
    private final Object pushToServerLock = new Object();

    private boolean         isPushingToServer;
    private IWandererServer server;
    private IPlaceDAO       placeDAO;
    private IPictureDAO     pictureDAO;

    private ConcurrentLinkedQueue<Integer> pendingTransactions;

    ToggleLikeJob(IWandererServer server, IPlaceDAO placeDAO, IPictureDAO pictureDAO) {
        this.server = server;
        this.placeDAO = placeDAO;
        this.pictureDAO = pictureDAO;
        this.pendingTransactions = new ConcurrentLinkedQueue<>();
        this.isPushingToServer = false;
    }

    private void pushToServerAux() {
        int id;

        if (pendingTransactions.isEmpty()) {
            placeDAO.clearPendingLikes();
            isPushingToServer = false;
            return;
        }

        id = pendingTransactions.peek();
        server
            .toggleLikeJob(id)
            .observeOn(Schedulers.newThread())
            .subscribe(new Observer<Void>() {
                @Override
                public void onCompleted() {
                    placeDAO.removePendingLike(pendingTransactions.poll());
                    pushToServerAux(); // Keep iterating on the queue
                }

                @Override
                public void onError(Throwable e) {
                    // Stop and wait for next toggling
                    isPushingToServer = false;
                    Timber.e(e, "Failed to toggle like");
                }

                @Override
                public void onNext(Void aVoid) {

                }
            });
    }

    private void pushToServer(int id) {
        if (!isPushingToServer) {
            synchronized (pushToServerLock) {
                if (!isPushingToServer) {
                    isPushingToServer = true;
                    pendingTransactions.clear();
                    placeDAO.getPendingLikes(pendingTransactions);
                    pendingTransactions.add(id);
                    placeDAO.savePendingLike(id);
                    pushToServerAux();
                    return;
                }
            }
        }

        pendingTransactions.add(id);
        placeDAO.savePendingLike(id);
    }

    public Observable<Place> create(Place place) {
        return Observable
            .create(new Observable.OnSubscribe<Place>() {
                @Override
                public void call(Subscriber<? super Place> subscriber) {
                    Place updatedPlace = placeDAO.toggleLike(place);
                    Picture mainPicture = pictureDAO.find(updatedPlace.getMainPicture().getId());

                    if (mainPicture != null) {
                        updatedPlace.setMainPicture(mainPicture);
                    }

                    subscriber.onNext(updatedPlace);
                    subscriber.onCompleted();

                    pushToServer(place.getId());
                }
            })
            .subscribeOn(Schedulers.newThread());
    }
}
