package com.adriencadet.wanderer.services.wanderer;

import com.adriencadet.wanderer.beans.Place;

import java.util.List;

/**
 * IPlaceSerializer
 * <p>
 */
interface IPlaceSerializer {
    Place fromWandererServer(PlaceDTO source);

    List<Place> fromWandererServer(List<PlaceDTO> source);
}
