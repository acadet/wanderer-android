package com.adriencadet.wanderer.models.services.wanderer;

import com.adriencadet.wanderer.models.services.wanderer.dto.PictureWandererServerDTO;
import com.adriencadet.wanderer.models.services.wanderer.dto.PlaceWandererServerDTO;

import java.util.List;

import rx.Observable;

/**
 * IWandererServer
 * <p>
 */
public interface IWandererServer {

    Observable<List<PlaceWandererServerDTO>> listPlacesByVisitDateDescJob();

    Observable<List<PictureWandererServerDTO>> listPicturesForPlaceJob(int placeID);

    Observable<Void> toggleLikeJob(int placeID, String deviceToken);
}
