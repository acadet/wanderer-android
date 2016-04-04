package com.adriencadet.wanderer.ui.helpers;

/**
 * IntFormatterHelper
 * <p>
 */
public class IntFormatterHelper {
    public static String userFriendly(int value) {
        if (value >= 1_000_000) {
            return String.format("%.2fM", value / 1_000_000.0);
        } else if (value >= 1_000) {
            return String.format("%.2fK", value / 1_000.0);
        } else {
            return String.format("%d", value);
        }
    }
}
