package com.adriencadet.wanderer.ui.controllers;

import android.view.View;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.ui.screens.fullscreen.HideFullscreenScreen;
import com.adriencadet.wanderer.ui.screens.fullscreen.LandingFullscreenScreen;
import com.lyft.scoop.Screen;
import com.lyft.scoop.ViewController;

/**
 * FullscreenController
 * <p>
 */
public class FullscreenController extends ViewController {

    @Override
    protected int layoutId() {
        return R.layout.fullscreen;
    }

    @Override
    public void onAttach() {
        super.onAttach();

        Screen screen = Screen.fromController(this);
        if (screen instanceof HideFullscreenScreen) {
            getView().setVisibility(View.GONE);
        } else if (screen instanceof LandingFullscreenScreen) {
            getView().setVisibility(View.VISIBLE);
        }
    }
}
