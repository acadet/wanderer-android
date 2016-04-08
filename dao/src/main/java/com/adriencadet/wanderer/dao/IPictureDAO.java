package com.adriencadet.wanderer.dao;

import com.adriencadet.wanderer.beans.Picture;

import java.util.List;

/**
 * IPictureDAO
 * <p>
 */
public interface IPictureDAO {
    Picture find(int id);

    List<Picture> listForPlace(int placeID);

    void save(Picture picture);

    void save(List<Picture> pictures);
}
