package com.adriencadet.wanderer.ui.screens;

import com.adriencadet.beans.Place;
import com.adriencadet.wanderer.ui.controllers.body.PlaceInsightController;
import com.lyft.scoop.Controller;
import com.lyft.scoop.Screen;

/**
 * PlaceInsightScreen
 * <p>
 */
@Controller(PlaceInsightController.class)
public class PlaceInsightScreen extends Screen {
    public Place place;

    public PlaceInsightScreen() {
        super();
    }

    public PlaceInsightScreen(Place place) {
        this();
        this.place = place;
    }

    public boolean hasPlace() {
        return place != null;
    }
}
