package com.adriencadet.wanderer.ui.screens;

import com.adriencadet.wanderer.beans.Place;
import com.adriencadet.wanderer.ui.controllers.body.IPlaceUpdateObserver;
import com.adriencadet.wanderer.ui.controllers.body.PlaceInsightController;
import com.lyft.scoop.Controller;
import com.lyft.scoop.Screen;

/**
 * PlaceInsightScreen
 * <p>
 */
@Controller(PlaceInsightController.class)
public class PlaceInsightScreen extends Screen {
    public Place                place;
    public IPlaceUpdateObserver observer;

    public PlaceInsightScreen() {
        super();
    }

    public PlaceInsightScreen(Place place) {
        this();
        this.place = place;
    }

    public PlaceInsightScreen(Place place, IPlaceUpdateObserver observer) {
        this(place);
        this.observer = observer;
    }

    public boolean hasPlace() {
        return place != null;
    }
}
