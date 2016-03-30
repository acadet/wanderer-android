package com.adriencadet.wanderer.models.bll.dto;

/**
 * PictureBLLDTO
 * <p>
 */
public class PictureBLLDTO {
    private int    id;
    private String url;

    public int getId() {
        return id;
    }

    public PictureBLLDTO setId(int id) {
        this.id = id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public PictureBLLDTO setUrl(String url) {
        this.url = url;
        return this;
    }
}
