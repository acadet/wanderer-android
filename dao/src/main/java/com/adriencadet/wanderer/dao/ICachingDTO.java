package com.adriencadet.wanderer.dao;

import java.util.Date;

/**
 * ICachingDTO
 * <p>
 */
interface ICachingDTO {
    int getId();

    Date getUpdatedAt();

    void setUpdatedAt(Date date);

    void removeFromRealm();
}
