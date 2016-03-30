package com.adriencadet.wanderer.models.serializers;

import com.adriencadet.wanderer.models.bll.dto.PlaceBLLDTO;
import com.adriencadet.wanderer.models.services.wanderer.dto.PlaceWandererServerDTO;

import java.util.List;

/**
 * IPlaceSerializer
 * <p>
 */
public interface IPlaceSerializer {
    PlaceBLLDTO serialize(PlaceWandererServerDTO source);

    List<PlaceBLLDTO> serialize(List<PlaceWandererServerDTO> source);
}
