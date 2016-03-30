package com.adriencadet.wanderer.models.services.wanderer.api;

import com.adriencadet.wanderer.models.services.wanderer.api.structs.ToggleLikeRequest;
import com.adriencadet.wanderer.models.services.wanderer.dto.PictureWandererServerDTO;
import com.adriencadet.wanderer.models.services.wanderer.dto.PlaceWandererServerDTO;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * IWandererAPI
 * <p>
 */
public interface IWandererAPI {
    String SUFFIX          = ".json";
    String PLACES_ENDPOINT = "/places";

    @GET(PLACES_ENDPOINT + SUFFIX)
    List<PlaceWandererServerDTO> listPlacesByVisitDateDesc();

    @GET(PLACES_ENDPOINT + "/{id}/pictures" + SUFFIX)
    List<PictureWandererServerDTO> listPicturesForPlace(@Path("id") int placeID);

    @PUT(PLACES_ENDPOINT + "/{id}/like" + SUFFIX)
    Void toggleLike(@Path("id") int placeID, @Body ToggleLikeRequest request);
}
