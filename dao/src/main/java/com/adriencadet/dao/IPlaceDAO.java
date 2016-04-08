package com.adriencadet.dao;

import java.util.List;

/**
 * IPlaceDAO
 * <p>
 */
public interface IPlaceDAO {
    List<PlaceDAODTO> listPlacesByVisitDateDescJob();

    void save(List<PlaceDAODTO> places);

    PlaceDAODTO toggleLike(int placeID);

    boolean hasEntries();

    PlaceDAODTO randomEntry();
}
