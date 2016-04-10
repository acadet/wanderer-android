package com.adriencadet.wanderer.services.wanderer;

import com.adriencadet.wanderer.beans.Picture;

import java.util.List;

/**
 * IPictureSerializer
 * <p>
 */
interface IPictureSerializer {
    Picture fromWandererServer(PictureDTO source);

    List<Picture> fromWandererServer(List<PictureDTO> source);
}
