package com.adriencadet.wanderer.models.serializers;

import com.adriencadet.beans.Picture;
import com.adriencadet.wanderer.models.dao.dto.PictureDAODTO;
import com.adriencadet.wanderer.models.services.wanderer.dto.PictureWandererServerDTO;

import java.util.List;

/**
 * IPictureSerializer
 * <p>
 */
public interface IPictureSerializer {
    Picture fromWandererServer(PictureWandererServerDTO source);

    List<Picture> fromWandererServer(List<PictureWandererServerDTO> source);

    Picture fromDAO(PictureDAODTO source);

    List<Picture> fromDAO(List<PictureDAODTO> source);

    PictureDAODTO toDAO(Picture source);

    List<PictureDAODTO> toDAO(List<Picture> source);
}
