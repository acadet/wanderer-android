package com.adriencadet.wanderer.models.bll;

import com.adriencadet.wanderer.models.bll.dto.PlaceBLLDTO;
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
    public Observable<PlaceBLLDTO> toggleLike(PlaceBLLDTO place) {
        return toggleLikeJob.create(place);
    }
}
