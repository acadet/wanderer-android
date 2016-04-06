package com.adriencadet.wanderer.ui.screens.popup;

import com.adriencadet.wanderer.ui.controllers.popup.PopupController;
import com.lyft.scoop.Controller;
import com.lyft.scoop.Screen;

/**
 * AlertScreen
 * <p>
 */
@Controller(PopupController.class)
public class AlertScreen extends Screen {
    public String message;

    public AlertScreen(String message) {
        super();
        this.message = message;
    }
}
