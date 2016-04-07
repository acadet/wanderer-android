package com.adriencadet.wanderer.ui.screens;

import com.adriencadet.wanderer.ui.controllers.body.PlaceListController;
import com.lyft.scoop.Controller;
import com.lyft.scoop.EnterTransition;
import com.lyft.scoop.ExitTransition;
import com.lyft.scoop.Screen;
import com.lyft.scoop.transitions.FadeTransition;

/**
 * PlaceListScreen
 * <p>
 */
@Controller(PlaceListController.class)
@EnterTransition(FadeTransition.class)
@ExitTransition(FadeTransition.class)
public class PlaceListScreen extends Screen {
}
