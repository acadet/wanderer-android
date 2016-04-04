package com.adriencadet.wanderer.models.bll;

import com.adriencadet.wanderer.models.bll.dto.PictureBLLDTO;
import com.adriencadet.wanderer.models.bll.dto.PlaceBLLDTO;
import com.adriencadet.wanderer.models.bll.jobs.CanUseRandomPlaceJob;
import com.adriencadet.wanderer.models.bll.jobs.ListPicturesForPlaceJob;
import com.adriencadet.wanderer.models.bll.jobs.ListPlacesByVisitDateDescJob;
import com.adriencadet.wanderer.models.bll.jobs.RandomPlaceJob;

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
    public Observable<List<PlaceBLLDTO>> listPlacesByVisitDateDesc() {
        return listPlacesByVisitDateDescJob.create();
    }

    @Override
    public Observable<List<PictureBLLDTO>> listPicturesForPlace(PlaceBLLDTO place) {
        return listPicturesForPlaceJob.create(place);
    }

    @Override
    public Observable<Boolean> canUseRandomPlace() {
        return canUseRandomPlaceJob.create();
    }

    @Override
    public Observable<PlaceBLLDTO> randomPlace() {
        return randomPlaceJob.create();
    }
}
