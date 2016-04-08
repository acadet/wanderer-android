package com.adriencadet.wanderer.models.serializers;

import com.adriencadet.wanderer.beans.beans.Place;
import com.adriencadet.wanderer.dao.dto.PlaceDAODTO;
import com.adriencadet.wanderer.services.wanderer.PlaceWandererServerDTO;

import java.util.List;

/**
 * IPlaceSerializer
 * <p>
 */
public interface IPlaceSerializer {
    Place fromWandererServer(PlaceWandererServerDTO source);

    List<Place> fromWandererServer(List<PlaceWandererServerDTO> source);
}
