package com.adriencadet.dao;

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
