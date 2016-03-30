package com.adriencadet.wanderer.models.services.wanderer;

import com.adriencadet.wanderer.models.services.wanderer.jobs.ListPicturesForPlaceJob;
import com.adriencadet.wanderer.models.services.wanderer.jobs.ListPlacesByVisitDateDescJob;
import com.adriencadet.wanderer.models.services.wanderer.jobs.ToggleLikeJob;

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
