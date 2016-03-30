package com.adriencadet.wanderer.models.services.wanderer.dto;

/**
 * PlaceWandererServerDTO
 * <p>
 */
public class PlaceWandererServerDTO {
    public int                      id;
    public String                   name;
    public String                   country;
    public double                   latitude;
    public double                   longitude;
    public String                   description;
    public String                   visit_date;
    public int                      likes;
    public PictureWandererServerDTO main_picture;
}
