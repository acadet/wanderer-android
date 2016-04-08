package com.adriencadet.wanderer.models.bll.jobs;

import com.adriencadet.wanderer.ApplicationConfiguration;
import com.adriencadet.wanderer.dao.IPictureDAO;
import com.adriencadet.wanderer.dao.IPlaceDAO;
import com.adriencadet.wanderer.models.serializers.IPictureSerializer;
import com.adriencadet.wanderer.models.serializers.IPlaceSerializer;
import com.adriencadet.wanderer.services.wanderer.IWandererServer;

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
    public ListPicturesForPlaceJob provideListPicturesForPlaceJob(ApplicationConfiguration configuration, IWandererServer server, IPictureSerializer serializer, IPictureDAO pictureDAO) {
        return new ListPicturesForPlaceJob(configuration, server, serializer, pictureDAO);
    }

    @Provides
    @Singleton
    public ListPlacesByVisitDateDescJob provideListPlacesByVisitDateDescJob(ApplicationConfiguration configuration, IWandererServer server, IPlaceSerializer serializer, IPictureSerializer pictureSerializer, IPictureDAO pictureDAO, IPlaceDAO placeDAO) {
        return new ListPlacesByVisitDateDescJob(configuration, server, serializer, pictureSerializer, placeDAO, pictureDAO);
    }

    @Provides
    @Singleton
    public RandomPlaceJob provideRandomPlaceJob(IPlaceSerializer placeSerializer, IPlaceDAO placeDAO) {
        return new RandomPlaceJob(placeSerializer, placeDAO);
    }

    @Provides
    @Singleton
    public CanUseRandomPlaceJob provideCanUseRandomPlaceJob(IPlaceDAO placeDAO) {
        return new CanUseRandomPlaceJob(placeDAO);
    }

    @Provides
    @Singleton
    public ToggleLikeJob provideToggleLikeJob(IWandererServer server, IPlaceSerializer placeSerializer, IPlaceDAO placeDAO) {
        return new ToggleLikeJob(server, placeSerializer, placeDAO);
    }
}
