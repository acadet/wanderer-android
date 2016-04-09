package com.adriencadet.wanderer.ui.screens;

import com.adriencadet.wanderer.ui.controllers.HiddenController;
import com.lyft.scoop.Controller;
import com.lyft.scoop.EnterTransition;
import com.lyft.scoop.Screen;
import com.lyft.scoop.transitions.FadeTransition;

/**
 * HideScreen
 * <p>
 */
@Controller(HiddenController.class)
@EnterTransition(FadeTransition.class)
public class HideScreen extends Screen {
}
