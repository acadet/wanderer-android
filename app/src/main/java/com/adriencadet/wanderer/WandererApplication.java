package com.adriencadet.wanderer;

import android.app.Application;

import com.adriencadet.wanderer.ui.helpers.ButterKnifeViewBinder;
import com.lyft.scoop.Scoop;

import timber.log.Timber;

/**
 * WandererApplication
 * <p>
 */
public class WandererApplication extends Application {
    private static WandererApplication  instance;
    private        ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        Scoop.setViewBinder(new ButterKnifeViewBinder());

        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(new ApplicationModule(this))
            .build();

        Timber.plant(new Timber.DebugTree());
    }

    public static ApplicationComponent getApplicationComponent() {
        return instance.applicationComponent;
    }
}
