package com.adriencadet.wanderer.models.serializers;

import com.adriencadet.wanderer.models.bll.dto.PlaceBLLDTO;
import com.adriencadet.wanderer.models.dao.dto.PlaceDAODTO;
import com.adriencadet.wanderer.models.services.wanderer.dto.PlaceWandererServerDTO;

import java.util.List;

/**
 * IPlaceSerializer
 * <p>
 */
public interface IPlaceSerializer {
    PlaceBLLDTO fromWandererServer(PlaceWandererServerDTO source);

    List<PlaceBLLDTO> fromWandererServer(List<PlaceWandererServerDTO> source);

    PlaceBLLDTO fromDAO(PlaceDAODTO source);

    List<PlaceBLLDTO> fromDAO(List<PlaceDAODTO> source);

    PlaceDAODTO toDAO(PlaceBLLDTO source);

    List<PlaceDAODTO> toDAO(List<PlaceBLLDTO> source);
}
