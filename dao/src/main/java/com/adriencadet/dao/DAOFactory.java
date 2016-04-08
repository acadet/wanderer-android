package com.adriencadet.dao;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.RealmConfiguration;

/**
 * DAOFactory
 * <p>
 */
@Module
public class DAOFactory {

    @Provides
    @Singleton
    Configuration provideConfiguration() {
        return new Configuration();
    }

    @Provides
    @Singleton
    RealmConfiguration provideRealmConfiguration(Context context) {
        return new RealmConfiguration.Builder(context)
            .name("wanderer.realm")
            .schemaVersion(1)
            .build();
    }

    @Provides
    @Singleton
    CachingModule provideCachingModule() {
        return new CachingModule();
    }

    @Provides
    @Singleton
    public IPictureDAO providePictureDAO(RealmConfiguration realmConfiguration, Configuration configuration, CachingModule cachingModule) {
        return new PictureDAO(realmConfiguration, configuration, cachingModule);
    }

    @Provides
    @Singleton
    public IPlaceDAO providePlaceDAO(RealmConfiguration realmConfiguration, Configuration configuration, CachingModule cachingModule) {
        return new PlaceDAO(realmConfiguration, configuration, cachingModule);
    }
}
