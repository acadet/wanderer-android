package com.adriencadet.wanderer.bll;

import com.adriencadet.wanderer.beans.Place;

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
