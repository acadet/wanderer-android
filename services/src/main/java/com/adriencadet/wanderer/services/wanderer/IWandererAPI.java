package com.adriencadet.wanderer.services.wanderer;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * IWandererAPI
 * <p>
 */
interface IWandererAPI {
    String SUFFIX          = ".json";
    String PLACES_ENDPOINT = "/places";

    @GET(PLACES_ENDPOINT + SUFFIX)
    List<PlaceDTO> listPlacesByVisitDateDesc();

    @GET(PLACES_ENDPOINT + "/{id}/pictures" + SUFFIX)
    List<PictureDTO> listPicturesForPlace(@Path("id") int placeID);

    @PUT(PLACES_ENDPOINT + "/{id}/like" + SUFFIX)
    Void toggleLike(@Path("id") int placeID);
}
