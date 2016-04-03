package com.adriencadet.wanderer.ui.events;

import com.adriencadet.wanderer.models.bll.dto.PlaceBLLDTO;

/**
 * SegueEvents
 * <p>
 */
public class SegueEvents {
    private SegueEvents() {
    }

    public static class Show {
        private Show() {
        }

        public static class PlaceInsight {
            public PlaceBLLDTO place;

            public PlaceInsight(PlaceBLLDTO place) {
                this.place = place;
            }
        }

        public static class PlaceMap {
        }

        public static class PlaceList {
        }

        public static class RandomPlace {
        }
    }

    public static class Exit {
        private Exit() {
        }

        public static class PlaceInsight {

        }
    }
}
