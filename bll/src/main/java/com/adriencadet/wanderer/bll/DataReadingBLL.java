package com.adriencadet.wanderer.bll;

import com.adriencadet.wanderer.beans.Picture;
import com.adriencadet.wanderer.beans.Place;

import java.util.List;

import rx.Observable;

/**
 * DataReadingBLL
 * <p>
 */
class DataReadingBLL implements IDataReadingBLL {
    private ListPlacesByVisitDateDescJob listPlacesByVisitDateDescJob;
    private ListPicturesForPlaceJob      listPicturesForPlaceJob;
    private RandomPlaceJob               randomPlaceJob;
    private CanUseRandomPlaceJob         canUseRandomPlaceJob;

    DataReadingBLL(
        ListPlacesByVisitDateDescJob listPlacesByVisitDateDescJob, ListPicturesForPlaceJob listPicturesForPlaceJob,
        RandomPlaceJob randomPlaceJob, CanUseRandomPlaceJob canUseRandomPlaceJob) {
        this.listPlacesByVisitDateDescJob = listPlacesByVisitDateDescJob;
        this.listPicturesForPlaceJob = listPicturesForPlaceJob;
        this.randomPlaceJob = randomPlaceJob;
        this.canUseRandomPlaceJob = canUseRandomPlaceJob;
    }

    @Override
    public Observable<List<Place>> listPlacesByVisitDateDesc() {
        return listPlacesByVisitDateDescJob.create();
    }

    @Override
    public Observable<List<Picture>> listPicturesForPlace(Place place) {
        return listPicturesForPlaceJob.create(place);
    }

    @Override
    public Observable<Boolean> canUseRandomPlace() {
        return canUseRandomPlaceJob.create();
    }

    @Override
    public Observable<Place> randomPlace() {
        return randomPlaceJob.create();
    }
}
