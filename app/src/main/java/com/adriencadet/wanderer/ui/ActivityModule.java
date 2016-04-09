package com.adriencadet.wanderer.ui;

import android.app.Activity;
import android.app.FragmentManager;

import com.adriencadet.wanderer.ui.components.Spinner;

import javax.inject.Singleton;

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
    @Singleton
    public Spinner provideSpinner(Activity activity) {
        return new Spinner(activity);
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
