package com.adriencadet.wanderer.ui.controllers;

import android.view.View;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.ui.screens.main.SetFullscreenScreen;
import com.lyft.scoop.Screen;
import com.lyft.scoop.ViewController;

import butterknife.Bind;

/**
 * MainController
 * <p>
 */
public class MainController extends ViewController {

    @Bind(R.id.main_fullscreen)
    View fullscreenView;

    @Override
    protected int layoutId() {
        return R.layout.main;
    }

    @Override
    public void onAttach() {
        super.onAttach();

        Screen screen = Screen.fromController(this);
        if (screen instanceof SetFullscreenScreen) {
            fullscreenView.setVisibility(View.VISIBLE);
        }
    }
}
