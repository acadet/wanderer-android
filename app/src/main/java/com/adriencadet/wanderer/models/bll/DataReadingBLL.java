package com.adriencadet.wanderer.models.bll;

import com.adriencadet.wanderer.models.bll.dto.PictureBLLDTO;
import com.adriencadet.wanderer.models.bll.dto.PlaceBLLDTO;
import com.adriencadet.wanderer.models.bll.jobs.ListPicturesForPlaceJob;
import com.adriencadet.wanderer.models.bll.jobs.ListPlacesByVisitDateDescJob;

import java.util.List;

import rx.Observable;

/**
 * DataReadingBLL
 * <p>
 */
class DataReadingBLL implements IDataReadingBLL {
    private ListPlacesByVisitDateDescJob listPlacesByVisitDateDescJob;
    private ListPicturesForPlaceJob      listPicturesForPlaceJob;

    DataReadingBLL(ListPlacesByVisitDateDescJob listPlacesByVisitDateDescJob, ListPicturesForPlaceJob listPicturesForPlaceJob) {
        this.listPlacesByVisitDateDescJob = listPlacesByVisitDateDescJob;
        this.listPicturesForPlaceJob = listPicturesForPlaceJob;
    }

    @Override
    public Observable<List<PlaceBLLDTO>> listPlacesByVisitDateDesc() {
        return listPlacesByVisitDateDescJob.create();
    }

    @Override
    public Observable<List<PictureBLLDTO>> listPicturesForPlace(PlaceBLLDTO place) {
        return listPicturesForPlaceJob.create(place);
    }
}
