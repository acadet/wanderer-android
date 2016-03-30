package com.adriencadet.wanderer.models.bll.dto;

import org.joda.time.DateTime;

/**
 * PlaceBLLDTO
 * <p>
 */
public class PlaceBLLDTO {
    private int           id;
    private String        name;
    private String        country;
    private double        latitude;
    private double        longitude;
    private String        description;
    private DateTime      visitDate;
    private int           likes;
    private PictureBLLDTO mainPicture;

    public int getId() {
        return id;
    }

    public PlaceBLLDTO setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PlaceBLLDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public PlaceBLLDTO setCountry(String country) {
        this.country = country;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public PlaceBLLDTO setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public PlaceBLLDTO setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PlaceBLLDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public DateTime getVisitDate() {
        return visitDate;
    }

    public PlaceBLLDTO setVisitDate(DateTime visitDate) {
        this.visitDate = visitDate;
        return this;
    }

    public int getLikes() {
        return likes;
    }

    public PlaceBLLDTO setLikes(int likes) {
        this.likes = likes;
        return this;
    }

    public PictureBLLDTO getMainPicture() {
        return mainPicture;
    }

    public PlaceBLLDTO setMainPicture(PictureBLLDTO mainPicture) {
        this.mainPicture = mainPicture;
        return this;
    }
}
