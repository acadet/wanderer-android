package com.adriencadet.wanderer.services.wanderer;

import com.adriencadet.wanderer.services.AndroidDevice;
import com.adriencadet.wanderer.services.Configuration;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * WandererServiceFactory
 * <p>
 */
@Module
public class WandererServiceFactory {
    @Provides
    RequestInterceptor provideRequestInterceptor(AndroidDevice device) {
        RequestInterceptor interceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("deviceToken", device.getIdentifier());
            }
        };

        return interceptor;
    }

    @Provides
    IWandererAPI provideAPI(Configuration configuration, RequestInterceptor requestInterceptor) {
        return new RestAdapter
            .Builder()
            .setEndpoint(configuration.WANDERER_SERVER_ENDPOINT)
            .setRequestInterceptor(requestInterceptor)
            .setConverter(new GsonConverter(new GsonBuilder().create()))
            .build()
            .create(IWandererAPI.class);
    }

    @Provides
    @Singleton
    IPictureSerializer providePictureSerializer() {
        return new PictureSerializer();
    }

    @Provides
    @Singleton
    IPlaceSerializer providePlaceSerializer(IPictureSerializer pictureSerializer) {
        return new PlaceSerializer(pictureSerializer);
    }

    @Provides
    @Singleton
    ListPlacesByVisitDateDescJob provideListPlacesByVisitDateDescJob(IWandererAPI api, IPlaceSerializer placeSerializer) {
        return new ListPlacesByVisitDateDescJob(api, placeSerializer);
    }

    @Provides
    @Singleton
    ListPicturesForPlaceJob provideListPicturesForPlaceJob(IWandererAPI api, IPictureSerializer pictureSerializer) {
        return new ListPicturesForPlaceJob(api, pictureSerializer);
    }

    @Provides
    @Singleton
    ToggleLikeJob provideToggleLikeJob(IWandererAPI api) {
        return new ToggleLikeJob(api);
    }

    @Provides
    @Singleton
    public IWandererServer provideIWandererServer(
        ListPlacesByVisitDateDescJob listPlacesByVisitDateDescJob,
        ListPicturesForPlaceJob listPicturesForPlaceJob,
        ToggleLikeJob toggleLikeJob
    ) {
        return new WandererServer(listPlacesByVisitDateDescJob, listPicturesForPlaceJob, toggleLikeJob);
    }
}
