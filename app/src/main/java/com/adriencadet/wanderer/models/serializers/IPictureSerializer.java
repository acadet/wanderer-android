package com.adriencadet.wanderer.models.serializers;

import com.adriencadet.wanderer.models.bll.dto.PictureBLLDTO;
import com.adriencadet.wanderer.models.services.wanderer.dto.PictureWandererServerDTO;

import java.util.List;

/**
 * IPictureSerializer
 * <p>
 */
public interface IPictureSerializer {
    PictureBLLDTO serialize(PictureWandererServerDTO source);

    List<PictureBLLDTO> serialize(List<PictureWandererServerDTO> source);
}
