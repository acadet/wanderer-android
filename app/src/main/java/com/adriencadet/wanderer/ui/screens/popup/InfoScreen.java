package com.adriencadet.wanderer.ui.screens.popup;

import com.adriencadet.wanderer.ui.controllers.PopupController;
import com.lyft.scoop.Controller;
import com.lyft.scoop.Screen;

/**
 * InfoScreen
 * <p>
 */
@Controller(PopupController.class)
public class InfoScreen extends Screen {
    public String message;

    public InfoScreen(String message) {
        super();
        this.message = message;
    }
}
