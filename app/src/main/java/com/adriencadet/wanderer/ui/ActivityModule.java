package com.adriencadet.wanderer.ui;

import android.app.Activity;
import android.app.FragmentManager;

import dagger.Module;
import dagger.Provides;

/**
 * ActivityModule
 * <p>
 */
@Module
public class ActivityModule {
    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    public Activity provideActivity() {
        return activity;
    }

    @Provides
    public FragmentManager provideFragmentManager() {
        return activity.getFragmentManager();
    }
}
