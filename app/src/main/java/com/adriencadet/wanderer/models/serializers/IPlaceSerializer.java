package com.adriencadet.wanderer.models.serializers;

import com.adriencadet.beans.Place;
import com.adriencadet.dao.dto.PlaceDAODTO;
import com.adriencadet.wanderer.models.services.wanderer.dto.PlaceWandererServerDTO;

import java.util.List;

/**
 * IPlaceSerializer
 * <p>
 */
public interface IPlaceSerializer {
    Place fromWandererServer(PlaceWandererServerDTO source);

    List<Place> fromWandererServer(List<PlaceWandererServerDTO> source);

    Place fromDAO(PlaceDAODTO source);

    List<Place> fromDAO(List<PlaceDAODTO> source);

    PlaceDAODTO toDAO(Place source);

    List<PlaceDAODTO> toDAO(List<Place> source);
}
