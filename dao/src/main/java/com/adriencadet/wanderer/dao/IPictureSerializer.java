package com.adriencadet.wanderer.dao;


import com.adriencadet.wanderer.beans.Picture;

import java.util.List;

/**
 * IPictureSerializer
 * <p>
 */
interface IPictureSerializer {
    Picture fromDAO(PictureDTO source);

    List<Picture> fromDAO(List<PictureDTO> source);

    PictureDTO toDAO(Picture source);

    List<PictureDTO> toDAO(List<Picture> source);
}
