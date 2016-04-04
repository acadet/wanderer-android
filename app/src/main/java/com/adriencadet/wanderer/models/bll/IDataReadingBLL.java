package com.adriencadet.wanderer.models.bll;

import com.adriencadet.wanderer.models.bll.dto.PictureBLLDTO;
import com.adriencadet.wanderer.models.bll.dto.PlaceBLLDTO;

import java.util.List;

import rx.Observable;

/**
 * IDataReadingBLL
 * <p>
 */
public interface IDataReadingBLL {
    Observable<List<PlaceBLLDTO>> listPlacesByVisitDateDesc();

    Observable<List<PictureBLLDTO>> listPicturesForPlace(PlaceBLLDTO place);

    Observable<Boolean> canUseRandomPlace();

    Observable<PlaceBLLDTO> randomPlace();
}
