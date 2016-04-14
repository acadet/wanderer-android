package com.adriencadet.wanderer.dao;

import com.adriencadet.wanderer.beans.Place;

import java.util.Collection;
import java.util.List;

/**
 * IPlaceDAO
 * <p>
 */
public interface IPlaceDAO {
    List<Place> listPlacesByVisitDateDescJob();

    void save(List<Place> places);

    Place toggleLike(Place place);

    void getPendingLikes(Collection<Integer> collectionToHydrate);

    void savePendingLikes(Collection<Integer> placeIds);

    boolean hasEntries();

    Place randomEntry();
}
