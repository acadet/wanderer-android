package com.adriencadet.wanderer;

import android.app.Application;

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

        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(new ApplicationModule(this))
            .build();
    }

    public static ApplicationComponent getApplicationComponent() {
        return instance.applicationComponent;
    }
}
