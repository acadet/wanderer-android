package com.adriencadet.beans;

/**
 * Picture
 * <p>
 */
public class Picture {
    private int    id;
    private String url;

    public int getId() {
        return id;
    }

    public Picture setId(int id) {
        this.id = id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Picture setUrl(String url) {
        this.url = url;
        return this;
    }
}
