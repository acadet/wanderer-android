package com.adriencadet.wanderer.beans;

import org.joda.time.DateTime;

/**
 * Place
 * <p>
 */
public class Place {
    private int      id;
    private String   name;
    private String   country;
    private double   latitude;
    private double   longitude;
    private String   description;
    private DateTime visitDate;
    private boolean  isLiking;
    private int      likes;
    private Picture  mainPicture;

    public int getId() {
        return id;
    }

    public Place setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Place setName(String name) {
        this.name = name;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Place setCountry(String country) {
        this.country = country;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public Place setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public Place setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Place setDescription(String description) {
        this.description = description;
        return this;
    }

    public DateTime getVisitDate() {
        return visitDate;
    }

    public Place setVisitDate(DateTime visitDate) {
        this.visitDate = visitDate;
        return this;
    }

    public boolean isLiking() {
        return isLiking;
    }

    public Place setLiking(boolean liking) {
        isLiking = liking;
        return this;
    }

    public int getLikes() {
        return likes;
    }

    public Place setLikes(int likes) {
        this.likes = likes;
        return this;
    }

    public Picture getMainPicture() {
        return mainPicture;
    }

    public Place setMainPicture(Picture mainPicture) {
        this.mainPicture = mainPicture;
        return this;
    }
}
