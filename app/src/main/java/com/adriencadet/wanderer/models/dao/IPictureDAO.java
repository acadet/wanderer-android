package com.adriencadet.wanderer.models.dao;

import com.adriencadet.wanderer.models.dao.dto.PictureDAODTO;

import java.util.List;

/**
 * IPictureDAO
 * <p>
 */
public interface IPictureDAO {
    PictureDAODTO find(int id);

    List<PictureDAODTO> listForPlace(int placeID);

    void save(PictureDAODTO picture);

    void save(List<PictureDAODTO> pictures);
}
