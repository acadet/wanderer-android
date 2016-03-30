package com.adriencadet.wanderer.models.serializers;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * SerializerFactory
 * <p>
 */
@Module
public class SerializerFactory {
    @Provides
    @Singleton
    public IPictureSerializer providePictureSerializer() {
        return new PictureSerializer();
    }

    @Provides
    @Singleton
    public IPlaceSerializer providePlaceSerializer(IPictureSerializer pictureSerializer) {
        return new PlaceSerializer(pictureSerializer);
    }
}
