package com.adriencadet.wanderer.services.wanderer;

import java.util.List;

import rx.Observable;

/**
 * WandererServer
 * <p>
 */
class WandererServer implements IWandererServer {
    private ListPlacesByVisitDateDescJob listPlacesByVisitDateDescJob;
    private ListPicturesForPlaceJob      listPicturesForPlaceJob;
    private ToggleLikeJob                toggleLikeJob;

    WandererServer(
        ListPlacesByVisitDateDescJob listPlacesByVisitDateDescJob,
        ListPicturesForPlaceJob listPicturesForPlaceJob,
        ToggleLikeJob toggleLikeJob
    ) {
        this.listPlacesByVisitDateDescJob = listPlacesByVisitDateDescJob;
        this.listPicturesForPlaceJob = listPicturesForPlaceJob;
        this.toggleLikeJob = toggleLikeJob;
    }

    @Override
    public Observable<List<PlaceDTO>> listPlacesByVisitDateDescJob() {
        return listPlacesByVisitDateDescJob.create();
    }

    @Override
    public Observable<List<PictureDTO>> listPicturesForPlaceJob(int placeID) {
        return listPicturesForPlaceJob.create(placeID);
    }

    @Override
    public Observable<Void> toggleLikeJob(int placeID) {
        return toggleLikeJob.create(placeID);
    }
}
