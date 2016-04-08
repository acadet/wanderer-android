package com.adriencadet.wanderer.services.wanderer;

import com.adriencadet.wanderer.services.AndroidDevice;
import com.adriencadet.wanderer.services.Configuration;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * WandererAPIFactory
 * <p>
 */
@Module
public class WandererAPIFactory {

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
}
