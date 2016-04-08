package com.adriencadet.wanderer.models.bll;


import com.adriencadet.wanderer.beans.beans.Picture;
import com.adriencadet.wanderer.beans.beans.Place;

import java.util.List;

import rx.Observable;

/**
 * IDataReadingBLL
 * <p>
 */
public interface IDataReadingBLL {
    Observable<List<Place>> listPlacesByVisitDateDesc();

    Observable<List<Picture>> listPicturesForPlace(Place place);

    Observable<Boolean> canUseRandomPlace();

    Observable<Place> randomPlace();
}
