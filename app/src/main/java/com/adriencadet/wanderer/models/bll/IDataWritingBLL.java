package com.adriencadet.wanderer.models.bll;

import com.adriencadet.wanderer.models.bll.dto.PlaceBLLDTO;

import rx.Observable;

/**
 * IDataWritingBLL
 * <p>
 */
public interface IDataWritingBLL {
    Observable<PlaceBLLDTO> toggleLike(PlaceBLLDTO place);
}
