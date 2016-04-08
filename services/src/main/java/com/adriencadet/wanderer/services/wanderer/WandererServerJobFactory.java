package com.adriencadet.wanderer.services.wanderer;

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
    ListPlacesByVisitDateDescJob provideListPlacesByVisitDateDescJob(IWandererAPI api) {
        return new ListPlacesByVisitDateDescJob(api);
    }

    @Provides
    @Singleton
    ListPicturesForPlaceJob provideListPicturesForPlaceJob(IWandererAPI api) {
        return new ListPicturesForPlaceJob(api);
    }

    @Provides
    @Singleton
    ToggleLikeJob provideToggleLikeJob(IWandererAPI api) {
        return new ToggleLikeJob(api);
    }
}
