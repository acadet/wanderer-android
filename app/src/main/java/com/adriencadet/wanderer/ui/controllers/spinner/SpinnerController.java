package com.adriencadet.wanderer.ui.controllers.spinner;

import android.app.Activity;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.ui.activities.BaseActivity;
import com.adriencadet.wanderer.ui.components.Spinner;
import com.adriencadet.wanderer.ui.screens.spinner.ShowSpinnerImmediatelyScreen;
import com.adriencadet.wanderer.ui.screens.spinner.ShowSpinnerSreen;
import com.lyft.scoop.Screen;
import com.lyft.scoop.ViewController;

import javax.inject.Inject;

/**
 * SpinnerController
 * <p>
 */
public class SpinnerController extends ViewController {

    private Spinner spinner;

    @Inject
    Activity activity;

    @Override
    protected int layoutId() {
        return R.layout.spinner_layout;
    }

    @Override
    public void onAttach() {
        super.onAttach();

        BaseActivity.getActivityComponent().inject(this);

        spinner = new Spinner(activity);

        Screen screen = Screen.fromController(this);

        if (screen instanceof ShowSpinnerImmediatelyScreen) {
            spinner.show();
        } else if (screen instanceof ShowSpinnerSreen) {
            spinner.show(true);
        } else {
            spinner.hide();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        spinner.hide();
    }
}
