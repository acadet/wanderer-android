package com.adriencadet.wanderer.models.services.wanderer.api;

import com.adriencadet.wanderer.ApplicationConfiguration;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * WandererAPIFactory
 * <p>
 */
@Module
public class WandererAPIFactory {
    @Provides
    public IWandererAPI provideAPI(ApplicationConfiguration configuration) {
        return new RestAdapter
            .Builder()
            .setEndpoint(configuration.WANDERER_SERVER_ENDPOINT)
            .setConverter(new GsonConverter(new GsonBuilder().create()))
            .build()
            .create(IWandererAPI.class);
    }
}
