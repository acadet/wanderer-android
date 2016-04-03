package com.adriencadet.wanderer.ui.activities;

import android.app.Activity;
import android.os.Bundle;

import com.adriencadet.wanderer.WandererApplication;
import com.adriencadet.wanderer.ui.FragmentModule;
import com.adriencadet.wanderer.ui.IFragmentComponent;
import com.adriencadet.wanderer.ui.components.Spinner;
import com.adriencadet.wanderer.ui.events.PopupEvents;
import com.adriencadet.wanderer.ui.events.SpinnerEvents;
import com.adriencadet.wanderer.ui.routers.AppRouter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;
import javax.inject.Named;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * BaseActivity
 * <p>
 */
public abstract class BaseActivity extends Activity {
    private static BaseActivity       instance;
    private        IFragmentComponent fragmentComponent;

    private Spinner spinner;

    @Inject
    AppRouter appRouter;

    @Inject
    @Named("popup")
    EventBus popupBus;

    @Inject
    @Named("spinner")
    EventBus spinnerBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WandererApplication.getApplicationComponent().inject(this);
        instance = this;
        fragmentComponent =
            WandererApplication
                .getApplicationComponent()
                .fragmentComponent(new FragmentModule(this));

        spinner = new Spinner(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        popupBus.register(this);
        spinnerBus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        popupBus.unregister(this);
        spinnerBus.unregister(this);

        Crouton.cancelAllCroutons();
    }

    protected void showSpinnerImmediately() {
        spinner.show(false);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConfirmPopup(PopupEvents.Confirm e) {
        Crouton.cancelAllCroutons();
        Crouton.makeText(this, e.message, Style.CONFIRM).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onInfoPopup(PopupEvents.Info e) {
        Crouton.cancelAllCroutons();
        Crouton.makeText(this, e.message, Style.INFO).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAlertPopup(PopupEvents.Alert e) {
        Crouton.cancelAllCroutons();
        Crouton.makeText(this, e.message, Style.ALERT).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHidePopup(PopupEvents.Hide e) {
        Crouton.clearCroutonsForActivity(this);
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

    public static IFragmentComponent getFragmentComponent() {
        return instance.fragmentComponent;
    }
}
