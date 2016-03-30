package com.adriencadet.wanderer.models.services.wanderer;

import com.adriencadet.wanderer.models.services.wanderer.dto.PictureWandererServerDTO;
import com.adriencadet.wanderer.models.services.wanderer.dto.PlaceWandererServerDTO;
import com.adriencadet.wanderer.models.services.wanderer.jobs.ListPicturesForPlaceJob;
import com.adriencadet.wanderer.models.services.wanderer.jobs.ListPlacesByVisitDateDescJob;
import com.adriencadet.wanderer.models.services.wanderer.jobs.ToggleLikeJob;

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
    public Observable<List<PlaceWandererServerDTO>> listPlacesByVisitDateDescJob() {
        return listPlacesByVisitDateDescJob.create();
    }

    @Override
    public Observable<List<PictureWandererServerDTO>> listPicturesForPlaceJob(int placeID) {
        return listPicturesForPlaceJob.create(placeID);
    }

    @Override
    public Observable<Void> toggleLikeJob(int placeID, String deviceToken) {
        return toggleLikeJob.create(placeID, deviceToken);
    }
}
