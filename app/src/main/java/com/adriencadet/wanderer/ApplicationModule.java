package com.adriencadet.wanderer;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * ApplicationModule
 * <p>
 */
@Module
public class ApplicationModule {
    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    public ApplicationConfiguration provideConfiguration() {
        return new ApplicationConfiguration();
    }

    @Provides
    @Singleton
    public SecretApplicationConfiguration provideSecretConfiguration() {
        return new SecretApplicationConfiguration();
    }
}
