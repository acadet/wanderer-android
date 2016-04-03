package com.adriencadet.wanderer.ui.helpers;

import com.adriencadet.wanderer.models.bll.dto.PlaceBLLDTO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * DateFormatterHelper
 * <p>
 */
public class DateFormatterHelper {
    public static String userFriendy(PlaceBLLDTO place) {
        DateFormat format;

        format = new SimpleDateFormat("MMM yyyy");

        return format.format(place.getVisitDate().toDate());
    }
}
