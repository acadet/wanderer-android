package com.adriencadet.wanderer.services.wanderer;

/**
 * PlaceDTO
 * <p>
 */
class PlaceDTO {
    public int        id;
    public String     name;
    public String     country;
    public double     latitude;
    public double     longitude;
    public String     description;
    public String     visit_date;
    public int        likes;
    public boolean    is_liking;
    public PictureDTO main_picture;
}
