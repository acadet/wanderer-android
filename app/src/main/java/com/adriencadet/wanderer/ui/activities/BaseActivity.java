package com.adriencadet.wanderer.ui.activities;

import android.app.Activity;
import android.os.Bundle;

import com.adriencadet.wanderer.WandererApplication;
import com.adriencadet.wanderer.ui.ActivityComponent;
import com.adriencadet.wanderer.ui.ActivityModule;
import com.adriencadet.wanderer.ui.components.Spinner;
import com.adriencadet.wanderer.ui.events.SpinnerEvents;
import com.adriencadet.wanderer.ui.routers.IRouter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * BaseActivity
 * <p>
 */
public abstract class BaseActivity extends Activity {
    private static BaseActivity      instance;
    private        ActivityComponent activityComponent;

    private Spinner spinner;

    @Inject
    @Named("app")
    IRouter appRouter;

    @Inject
    @Named("popup")
    IRouter popupRouter;

    @Inject
    @Named("spinner")
    EventBus spinnerBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WandererApplication.getApplicationComponent().inject(this);
        instance = this;
        activityComponent =
            WandererApplication
                .getApplicationComponent()
                .fragmentComponent(new ActivityModule(this));

        spinner = new Spinner(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        spinnerBus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        spinnerBus.unregister(this);

    }

    protected void showSpinnerImmediately() {
        spinner.show(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSpinnerShow(SpinnerEvents.Show e) {
        spinner.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSpinnerShowImmediately(SpinnerEvents.ShowImmediately e) {
        showSpinnerImmediately();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSpinnerHide(SpinnerEvents.Hide e) {
        spinner.hide();
    }

    public static ActivityComponent getActivityComponent() {
        return instance.activityComponent;
    }
}
