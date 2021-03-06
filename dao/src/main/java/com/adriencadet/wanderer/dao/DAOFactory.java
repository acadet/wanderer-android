package com.adriencadet.wanderer.dao;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Named;
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
    @Named("places")
    SharedPreferences providePlaceStore(Context context) {
        return context.getSharedPreferences("places", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    CachingModule provideCachingModule() {
        return new CachingModule();
    }

    @Provides
    @Singleton
    IPlaceSerializer providePlaceSerializer() {
        return new PlaceSerializer();
    }

    @Provides
    @Singleton
    IPictureSerializer providePictureSerializer() {
        return new PictureSerializer();
    }

    @Provides
    @Singleton
    public IPictureDAO providePictureDAO(RealmConfiguration realmConfiguration, Configuration configuration, CachingModule cachingModule, IPictureSerializer pictureSerializer) {
        return new PictureDAO(realmConfiguration, configuration, cachingModule, pictureSerializer);
    }

    @Provides
    @Singleton
    public IPlaceDAO providePlaceDAO(RealmConfiguration realmConfiguration, Configuration configuration, @Named("places") SharedPreferences store, CachingModule cachingModule, IPlaceSerializer placeSerializer) {
        return new PlaceDAO(realmConfiguration, configuration, store, cachingModule, placeSerializer);
    }
}
