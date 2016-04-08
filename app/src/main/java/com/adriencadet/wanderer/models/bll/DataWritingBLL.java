package com.adriencadet.wanderer.models.bll;

import com.adriencadet.wanderer.beans.beans.Place;
import com.adriencadet.wanderer.models.bll.jobs.ToggleLikeJob;

import rx.Observable;

/**
 * DataWritingBLL
 * <p>
 */
class DataWritingBLL implements IDataWritingBLL {
    private ToggleLikeJob toggleLikeJob;

    DataWritingBLL(ToggleLikeJob toggleLikeJob) {
        this.toggleLikeJob = toggleLikeJob;
    }

    @Override
    public Observable<Place> toggleLike(Place place) {
        return toggleLikeJob.create(place);
    }
}
