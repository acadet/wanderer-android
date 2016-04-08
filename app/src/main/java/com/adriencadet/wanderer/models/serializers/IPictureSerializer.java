package com.adriencadet.wanderer.models.serializers;

import com.adriencadet.wanderer.beans.beans.Picture;
import com.adriencadet.wanderer.dao.dto.PictureDAODTO;
import com.adriencadet.wanderer.models.services.wanderer.dto.PictureWandererServerDTO;

import java.util.List;

/**
 * IPictureSerializer
 * <p>
 */
public interface IPictureSerializer {
    Picture fromWandererServer(PictureWandererServerDTO source);

    List<Picture> fromWandererServer(List<PictureWandererServerDTO> source);
}
