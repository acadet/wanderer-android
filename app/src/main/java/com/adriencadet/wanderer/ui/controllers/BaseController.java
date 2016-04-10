package com.adriencadet.wanderer.ui.controllers;

import android.content.Context;

import com.adriencadet.wanderer.WandererApplication;
import com.lyft.scoop.ViewController;

import javax.inject.Inject;

/**
 * BaseController
 * <p>
 */
public abstract class BaseController extends ViewController {
    @Inject
    public Context context;

    @Override
    public void onAttach() {
        super.onAttach();
        WandererApplication.getApplicationComponent().inject(this);
    }
}
