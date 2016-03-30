package com.adriencadet.wanderer.models.services.wanderer.jobs;

import com.adriencadet.wanderer.models.services.wanderer.api.IWandererAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * WandererServerJobFactory
 * <p>
 */
@Module
public class WandererServerJobFactory {
    @Provides
    @Singleton
    public ListPlacesByVisitDateDescJob provideListPlacesByVisitDateDescJob(IWandererAPI api) {
        return new ListPlacesByVisitDateDescJob(api);
    }

    @Provides
    @Singleton
    public ListPicturesForPlaceJob provideListPicturesForPlaceJob(IWandererAPI api) {
        return new ListPicturesForPlaceJob(api);
    }

    @Provides
    @Singleton
    public ToggleLikeJob provideToggleLikeJob(IWandererAPI api) {
        return new ToggleLikeJob(api);
    }
}
