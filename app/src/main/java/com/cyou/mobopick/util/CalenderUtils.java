package com.cyou.mobopick.util;

import java.util.Calendar;

/**
 * Created by chengfei on 14-10-24.
 */
public class CalenderUtils {

    public static boolean isSameYear(Calendar calendar1, Calendar calendar2) {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);
    }

    public static boolean isSameDay(Calendar calendar1, Calendar calendar2) {
        return calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR);
    }
}
