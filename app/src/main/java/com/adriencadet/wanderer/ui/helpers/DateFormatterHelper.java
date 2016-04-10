package com.adriencadet.wanderer.ui.helpers;

import com.adriencadet.wanderer.beans.Place;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * DateFormatterHelper
 * <p>
 */
public class DateFormatterHelper {
    public static String userFriendy(Place place) {
        DateFormat format;

        format = new SimpleDateFormat("MMM yyyy");

        return format.format(place.getVisitDate().toDate());
    }
}
