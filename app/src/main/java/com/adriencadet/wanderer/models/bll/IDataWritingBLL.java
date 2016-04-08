package com.adriencadet.wanderer.models.bll;

import com.adriencadet.wanderer.beans.beans.Place;

import rx.Observable;

/**
 * IDataWritingBLL
 * <p>
 */
public interface IDataWritingBLL {
    Observable<Place> toggleLike(Place place);
}
