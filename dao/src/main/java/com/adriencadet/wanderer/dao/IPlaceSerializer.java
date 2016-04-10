package com.adriencadet.wanderer.dao;

import com.adriencadet.wanderer.beans.Place;

import java.util.List;

/**
 * IPlaceSerializer
 * <p>
 */
interface IPlaceSerializer {

    Place fromDAO(PlaceDTO source);

    List<Place> fromDAO(List<PlaceDTO> source);

    PlaceDTO toDAO(Place source);

    List<PlaceDTO> toDAO(List<Place> source);
}
