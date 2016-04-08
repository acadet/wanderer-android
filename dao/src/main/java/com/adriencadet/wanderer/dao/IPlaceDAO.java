package com.adriencadet.wanderer.dao;

import com.adriencadet.wanderer.beans.Place;

import java.util.List;

/**
 * IPlaceDAO
 * <p>
 */
public interface IPlaceDAO {
    List<Place> listPlacesByVisitDateDescJob();

    void save(List<Place> places);

    Place toggleLike(int placeID);

    boolean hasEntries();

    Place randomEntry();
}
