package com.adriencadet.wanderer.ui.screens.fullscreen;

import com.adriencadet.wanderer.ui.controllers.FullscreenController;
import com.lyft.scoop.Controller;
import com.lyft.scoop.EnterTransition;
import com.lyft.scoop.ExitTransition;
import com.lyft.scoop.Screen;
import com.lyft.scoop.transitions.DownwardSlideTransition;
import com.lyft.scoop.transitions.UpwardSlideTransition;

/**
 * LandingFullscreenScreen
 * <p>
 */
@Controller(FullscreenController.class)
@EnterTransition(UpwardSlideTransition.class)
@ExitTransition(DownwardSlideTransition.class)
public class LandingFullscreenScreen extends Screen {
}
