package com.adriencadet.wanderer.ui.events;

/**
 * PopupEvents
 * <p>
 */
public class PopupEvents {
    private PopupEvents() {
    }

    static class PopupEvent {
        public String message;

        PopupEvent(String message) {
            this.message = message;
        }
    }

    public static class Info extends PopupEvent {
        public Info(String message) {
            super(message);
        }
    }

    public static class Confirm extends PopupEvent {
        public Confirm(String message) {
            super(message);
        }
    }

    public static class Alert extends PopupEvent {
        public Alert(String message) {
            super(message);
        }
    }

    public static class Hide {
    }
}
