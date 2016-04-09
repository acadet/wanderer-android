package com.adriencadet.wanderer.bll;


import com.adriencadet.wanderer.beans.Place;

import rx.Observable;

/**
 * IDataWritingBLL
 * <p>
 */
public interface IDataWritingBLL {
    Observable<Place> toggleLike(Place place);
}
