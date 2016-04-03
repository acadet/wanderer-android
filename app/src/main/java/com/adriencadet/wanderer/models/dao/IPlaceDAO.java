package com.adriencadet.wanderer.models.dao;

import com.adriencadet.wanderer.models.dao.dto.PlaceDAODTO;

import java.util.List;

/**
 * IPlaceDAO
 * <p>
 */
public interface IPlaceDAO {
    List<PlaceDAODTO> listPlacesByVisitDateDescJob();

    void save(List<PlaceDAODTO> places);

    PlaceDAODTO randomEntry();
}
