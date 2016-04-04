package com.adriencadet.wanderer.models.dao.dto;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * PlaceDAODTO
 * <p>
 */
public class PlaceDAODTO extends RealmObject {
    @PrimaryKey
    private int id;

    private String  name;
    private String  country;
    private double  latitude;
    private double  longitude;
    private String  description;
    private Date    visitDate;
    private boolean isLiking;
    private int     likes;
    private int     mainPictureID;

    private Date updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public boolean isLiking() {
        return isLiking;
    }

    public void setLiking(boolean liking) {
        isLiking = liking;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getMainPictureID() {
        return mainPictureID;
    }

    public void setMainPictureID(int mainPictureID) {
        this.mainPictureID = mainPictureID;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
