package com.adriencadet.wanderer.models.bll.jobs;

import com.adriencadet.wanderer.models.serializers.IPictureSerializer;
import com.adriencadet.wanderer.models.serializers.IPlaceSerializer;
import com.adriencadet.wanderer.models.services.wanderer.IWandererServer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * BLLJobFactory
 * <p>
 */
@Module
public class BLLJobFactory {
    @Provides
    @Singleton
    public ListPicturesForPlaceJob listPicturesForPlaceJob(IWandererServer server, IPictureSerializer serializer) {
        return new ListPicturesForPlaceJob(server, serializer);
    }

    @Provides
    @Singleton
    public ListPlacesByVisitDateDescJob listPlacesByVisitDateDescJob(IWandererServer server, IPlaceSerializer serializer) {
        return new ListPlacesByVisitDateDescJob(server, serializer);
    }
}
