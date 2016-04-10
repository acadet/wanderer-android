package com.adriencadet.wanderer.services.wanderer;

import com.adriencadet.wanderer.beans.Picture;
import com.adriencadet.wanderer.beans.Place;

import java.util.List;

import rx.Observable;

/**
 * IWandererServer
 * <p>
 */
public interface IWandererServer {
    Observable<List<Place>> listPlacesByVisitDateDescJob();

    Observable<List<Picture>> listPicturesForPlaceJob(int placeID);

    Observable<Void> toggleLikeJob(int placeID);
}
