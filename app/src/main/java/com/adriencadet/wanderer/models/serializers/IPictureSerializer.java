package com.adriencadet.wanderer.models.serializers;

import com.adriencadet.wanderer.models.bll.dto.PictureBLLDTO;
import com.adriencadet.wanderer.models.dao.dto.PictureDAODTO;
import com.adriencadet.wanderer.models.services.wanderer.dto.PictureWandererServerDTO;

import java.util.List;

/**
 * IPictureSerializer
 * <p>
 */
public interface IPictureSerializer {
    PictureBLLDTO fromWandererServer(PictureWandererServerDTO source);

    List<PictureBLLDTO> fromWandererServer(List<PictureWandererServerDTO> source);

    PictureBLLDTO fromDAO(PictureDAODTO source);

    List<PictureBLLDTO> fromDAO(List<PictureDAODTO> source);

    PictureDAODTO toDAO(PictureBLLDTO source);

    List<PictureDAODTO> toDAO(List<PictureBLLDTO> source);
}
