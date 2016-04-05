package com.adriencadet.wanderer.models.dao;

import android.content.Context;

import com.adriencadet.wanderer.ApplicationConfiguration;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * DAOFactory
 * <p>
 */
@Module
public class DAOFactory {

    @Provides
    @Singleton
    CachingModule provideCachingModule() {
        return new CachingModule();
    }

    @Provides
    @Singleton
    public IPictureDAO providePictureDAO(Context context, ApplicationConfiguration configuration, CachingModule cachingModule) {
        return new PictureDAO(context, configuration, cachingModule);
    }

    @Provides
    @Singleton
    public IPlaceDAO providePlaceDAO(Context context, ApplicationConfiguration configuration, CachingModule cachingModule) {
        return new PlaceDAO(context, configuration, cachingModule);
    }
}
