package com.adriencadet.dao;

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
