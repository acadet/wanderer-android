package com.adriencadet.wanderer.models.bll;

import com.adriencadet.wanderer.models.bll.jobs.CanUseRandomPlaceJob;
import com.adriencadet.wanderer.models.bll.jobs.ListPicturesForPlaceJob;
import com.adriencadet.wanderer.models.bll.jobs.ListPlacesByVisitDateDescJob;
import com.adriencadet.wanderer.models.bll.jobs.RandomPlaceJob;
import com.adriencadet.wanderer.models.bll.jobs.ToggleLikeJob;

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
