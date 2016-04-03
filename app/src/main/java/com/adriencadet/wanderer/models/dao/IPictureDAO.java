package com.adriencadet.wanderer.models.dao;

import com.adriencadet.wanderer.models.dao.dto.PictureDAODTO;

import java.util.List;

/**
 * IPictureDAO
 * <p>
 */
public interface IPictureDAO {
    List<PictureDAODTO> listForPlace(int placeID);

    void save(List<PictureDAODTO> pictures);
}
