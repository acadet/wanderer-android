package com.adriencadet.wanderer.services.wanderer;

import java.util.List;

import rx.Observable;

/**
 * IWandererServer
 * <p>
 */
public interface IWandererServer {
    Observable<List<PlaceDTO>> listPlacesByVisitDateDescJob();

    Observable<List<PictureDTO>> listPicturesForPlaceJob(int placeID);

    Observable<Void> toggleLikeJob(int placeID);
}
