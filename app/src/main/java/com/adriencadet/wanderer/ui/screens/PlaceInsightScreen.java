package com.adriencadet.wanderer.ui.screens;

import com.adriencadet.wanderer.models.bll.dto.PlaceBLLDTO;
import com.adriencadet.wanderer.ui.controllers.body.PlaceInsightController;
import com.lyft.scoop.Controller;
import com.lyft.scoop.Screen;

/**
 * PlaceInsightScreen
 * <p>
 */
@Controller(PlaceInsightController.class)
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
