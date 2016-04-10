package com.adriencadet.wanderer.ui.screens.popup;

import com.adriencadet.wanderer.ui.controllers.popup.PopupController;
import com.lyft.scoop.Controller;
import com.lyft.scoop.Screen;

/**
 * WarnScreen
 * <p>
 */
@Controller(PopupController.class)
public class WarnScreen extends Screen {
    public String message;

    public WarnScreen(String message) {
        this.message = message;
    }
}
