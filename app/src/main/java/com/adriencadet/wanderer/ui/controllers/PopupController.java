package com.adriencadet.wanderer.ui.controllers;

import android.app.Activity;

import com.adriencadet.wanderer.R;
import com.adriencadet.wanderer.ui.screens.popup.AlertScreen;
import com.adriencadet.wanderer.ui.screens.popup.ConfirmScreen;
import com.adriencadet.wanderer.ui.screens.popup.InfoScreen;
import com.lyft.scoop.Screen;
import com.lyft.scoop.ViewController;

import javax.inject.Inject;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * PopupController
 * <p>
 */
public class PopupController extends ViewController {

    @Inject
    Activity activity;

    @Override
    protected int layoutId() {
        return R.layout.popup;
    }

    @Override
    public void onAttach() {
        super.onAttach();

        //TODO: injection

        Screen notification = Screen.fromController(this);
        String message;
        Style style;

        if (notification instanceof AlertScreen) {
            AlertScreen e = (AlertScreen) notification;
            message = e.message;
            style = Style.ALERT;
        } else if (notification instanceof ConfirmScreen) {
            ConfirmScreen e = (ConfirmScreen) notification;
            message = e.message;
            style = Style.CONFIRM;
        } else {
            InfoScreen e = (InfoScreen) notification;
            message = e.message;
            style = Style.INFO;
        }

        Crouton.cancelAllCroutons();
        Crouton.makeText(activity, message, style).show();
    }
}
