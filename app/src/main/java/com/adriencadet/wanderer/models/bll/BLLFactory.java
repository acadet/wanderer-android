package com.adriencadet.wanderer.models.bll;

import com.adriencadet.wanderer.models.bll.jobs.ListPicturesForPlaceJob;
import com.adriencadet.wanderer.models.bll.jobs.ListPlacesByVisitDateDescJob;

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
    public IDataReadingBLL provideDataReadingBLL(ListPlacesByVisitDateDescJob listPlacesByVisitDateDescJob, ListPicturesForPlaceJob listPicturesForPlaceJob) {
        return new DataReadingBLL(listPlacesByVisitDateDescJob, listPicturesForPlaceJob);
    }
}
