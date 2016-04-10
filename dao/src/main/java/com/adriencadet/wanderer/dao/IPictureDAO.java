package com.adriencadet.wanderer.dao;

import com.adriencadet.wanderer.beans.Picture;
import com.adriencadet.wanderer.beans.Place;

import java.util.List;

/**
 * IPictureDAO
 * <p>
 */
public interface IPictureDAO {
    Picture find(int id);

    List<Picture> listForPlace(int placeID);

    void save(Place relatedPlace, Picture picture);

    void save(Place relatedPlace, List<Picture> pictures);
}
