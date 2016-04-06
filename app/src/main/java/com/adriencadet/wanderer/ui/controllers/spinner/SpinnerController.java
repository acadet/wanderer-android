package com.adriencadet.wanderer.ui.controllers.spinner;

import android.content.Context;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.WandererApplication;
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
    Context context;

    @Override
    protected int layoutId() {
        return R.layout.spinner_layout;
    }

    @Override
    public void onAttach() {
        super.onAttach();

        WandererApplication.getApplicationComponent().inject(this);

        spinner = new Spinner(context);

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
