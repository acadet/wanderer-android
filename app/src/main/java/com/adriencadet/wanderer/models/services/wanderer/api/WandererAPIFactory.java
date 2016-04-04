package com.adriencadet.wanderer.models.services.wanderer.api;

import com.adriencadet.wanderer.ApplicationConfiguration;
import com.adriencadet.wanderer.models.structs.AndroidDevice;
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
    public RequestInterceptor provideRequestInterceptor(AndroidDevice device) {
        RequestInterceptor interceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("deviceToken", device.getIdentifier());
            }
        };

        return interceptor;
    }

    @Provides
    public IWandererAPI provideAPI(ApplicationConfiguration configuration, RequestInterceptor requestInterceptor) {
        return new RestAdapter
            .Builder()
            .setEndpoint(configuration.WANDERER_SERVER_ENDPOINT)
            .setRequestInterceptor(requestInterceptor)
            .setConverter(new GsonConverter(new GsonBuilder().create()))
            .build()
            .create(IWandererAPI.class);
    }
}
