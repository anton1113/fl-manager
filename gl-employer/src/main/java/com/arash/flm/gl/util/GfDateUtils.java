package com.arash.flm.gl.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by anton on 29.04.18.
 *
 */
public class GfDateUtils {

    public static final SimpleDateFormat GF_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy hh:mm");

    public static Date parseDate(String formattedDate, SimpleDateFormat dateFormat) {
        try {
            return dateFormat.parse(formattedDate);
        } catch (ParseException e) {
            return null;
            // TODO logging
        }
    }
}
