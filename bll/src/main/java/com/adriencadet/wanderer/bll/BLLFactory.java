package com.adriencadet.wanderer.bll;

import com.adriencadet.wanderer.dao.IPictureDAO;
import com.adriencadet.wanderer.dao.IPlaceDAO;
import com.adriencadet.wanderer.services.wanderer.IWandererServer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * BLLFactory
 * <p>
 */
@Module
public class BLLFactory {

    @Provides
    @Singleton
    Configuration provideConfiguration() {
        return new Configuration();
    }

    @Provides
    @Singleton
    ListPicturesForPlaceJob provideListPicturesForPlaceJob(Configuration configuration, IWandererServer server, IPictureDAO pictureDAO) {
        return new ListPicturesForPlaceJob(configuration, server, pictureDAO);
    }

    @Provides
    @Singleton
    ListPlacesByVisitDateDescJob provideListPlacesByVisitDateDescJob(Configuration configuration, IWandererServer server, IPictureDAO pictureDAO, IPlaceDAO placeDAO) {
        return new ListPlacesByVisitDateDescJob(configuration, server, placeDAO, pictureDAO);
    }

    @Provides
    @Singleton
    RandomPlaceJob provideRandomPlaceJob(IPlaceDAO placeDAO) {
        return new RandomPlaceJob(placeDAO);
    }

    @Provides
    @Singleton
    CanUseRandomPlaceJob provideCanUseRandomPlaceJob(IPlaceDAO placeDAO) {
        return new CanUseRandomPlaceJob(placeDAO);
    }

    @Provides
    @Singleton
    ToggleLikeJob provideToggleLikeJob(IWandererServer server, IPlaceDAO placeDAO, IPictureDAO pictureDAO) {
        return new ToggleLikeJob(server, placeDAO, pictureDAO);
    }

    @Provides
    @Singleton
    public IDataReadingBLL provideDataReadingBLL(
        ListPlacesByVisitDateDescJob listPlacesByVisitDateDescJob, ListPicturesForPlaceJob listPicturesForPlaceJob,
        RandomPlaceJob randomPlaceJob, CanUseRandomPlaceJob canUseRandomPlaceJob) {
        return new DataReadingBLL(listPlacesByVisitDateDescJob, listPicturesForPlaceJob, randomPlaceJob, canUseRandomPlaceJob);
    }

    @Provides
    @Singleton
    public IDataWritingBLL provideDataWritingBLL(ToggleLikeJob toggleLikeJob) {
        return new DataWritingBLL(toggleLikeJob);
    }
}
