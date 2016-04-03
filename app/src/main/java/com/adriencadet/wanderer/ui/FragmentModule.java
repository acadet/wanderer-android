package com.adriencadet.wanderer.ui;

import android.app.Activity;
import android.app.FragmentManager;

import dagger.Module;
import dagger.Provides;

/**
 * FragmentModule
 * <p>
 */
@Module
public class FragmentModule {
    private Activity activity;

    public FragmentModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    public FragmentManager provideFragmentManager() {
        return activity.getFragmentManager();
    }
}
