package com.adriencadet.wanderer.models.dao.dto;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * PictureDAODTO
 * <p>
 */
public class PictureDAODTO extends RealmObject {
    @PrimaryKey
    private int    id;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
