package com.adriencadet.wanderer.models.dao.dto;

import java.util.Date;

/**
 * ICachingDTO
 * <p>
 */
public interface ICachingDTO {
    int getId();

    Date getUpdatedAt();

    void setUpdatedAt(Date date);

    void removeFromRealm();
}
