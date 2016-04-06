package com.adriencadet.wanderer.ui.screens.popup;

import com.adriencadet.wanderer.ui.controllers.popup.PopupController;
import com.lyft.scoop.Controller;
import com.lyft.scoop.Screen;

/**
 * ConfirmScreen
 * <p>
 */
@Controller(PopupController.class)
public class ConfirmScreen extends Screen {
    public String message;

    public ConfirmScreen(String message) {
        super();
        this.message = message;
    }
}
