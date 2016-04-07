package com.adriencadet.wanderer.ui.activities;

import android.app.Activity;
import android.os.Bundle;

import com.adriencadet.wanderer.WandererApplication;
import com.adriencadet.wanderer.ui.ActivityComponent;
import com.adriencadet.wanderer.ui.ActivityModule;
import com.adriencadet.wanderer.ui.routers.IRouter;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * BaseActivity
 * <p>
 */
public abstract class BaseActivity extends Activity {
    private static BaseActivity      instance;
    private        ActivityComponent activityComponent;

    @Inject
    @Named("main")
    IRouter appRouter;

    @Inject
    @Named("popup")
    IRouter popupRouter;

    @Inject
    @Named("spinner")
    IRouter spinnerRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WandererApplication.getApplicationComponent().inject(this);
        instance = this;
        activityComponent =
            WandererApplication
                .getApplicationComponent()
                .fragmentComponent(new ActivityModule(this));
    }

    public static ActivityComponent getActivityComponent() {
        return instance.activityComponent;
    }
}
