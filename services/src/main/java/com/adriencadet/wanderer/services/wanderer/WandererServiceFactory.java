package com.adriencadet.wanderer.services.wanderer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * WandererServiceFactory
 * <p>
 */
@Module
public class WandererServiceFactory {
    @Provides
    @Singleton
    public IWandererServer provideIWandererServer(
        ListPlacesByVisitDateDescJob listPlacesByVisitDateDescJob,
        ListPicturesForPlaceJob listPicturesForPlaceJob,
        ToggleLikeJob toggleLikeJob
    ) {
        return new WandererServer(listPlacesByVisitDateDescJob, listPicturesForPlaceJob, toggleLikeJob);
    }
}
