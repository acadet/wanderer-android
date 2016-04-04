package com.adriencadet.wanderer.ui.screens;

import com.adriencadet.wanderer.models.bll.dto.PlaceBLLDTO;
import com.adriencadet.wanderer.ui.controllers.PlaceInsightController;
import com.lyft.scoop.Controller;
import com.lyft.scoop.EnterTransition;
import com.lyft.scoop.ExitTransition;
import com.lyft.scoop.Screen;
import com.lyft.scoop.transitions.DownwardSlideTransition;
import com.lyft.scoop.transitions.UpwardSlideTransition;

/**
 * PlaceInsightScreen
 * <p>
 */
@Controller(PlaceInsightController.class)
@EnterTransition(UpwardSlideTransition.class)
@ExitTransition(DownwardSlideTransition.class)
public class PlaceInsightScreen extends Screen {
    public PlaceBLLDTO place;

    public PlaceInsightScreen() {
        super();
    }

    public PlaceInsightScreen(PlaceBLLDTO place) {
        this();
        this.place = place;
    }

    public boolean hasPlace() {
        return place != null;
    }
}
