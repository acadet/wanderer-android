package com.adriencadet.wanderer.ui.events;

import com.adriencadet.wanderer.models.bll.dto.PlaceBLLDTO;

/**
 * SegueEvents
 * <p>
 */
public class SegueEvents {
    private SegueEvents() {
    }

    public static class ShowPlaceInsight {
        public PlaceBLLDTO place;

        public ShowPlaceInsight(PlaceBLLDTO place) {
            this.place = place;
        }
    }
}
