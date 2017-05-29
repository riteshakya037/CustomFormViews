package com.ritesh.customfieldviews.commons;

/**
 * Created by Ritesh on 0029, May 29, 2017.
 */

public class StringUtils {
    public static boolean isNotNull(String field) {
        return !isNull(field);
    }

    public static boolean isNull(String field) {
        if (field == null)
            return true;
        else
            field = field.trim();

        return (field.equalsIgnoreCase("NULL") || field.equalsIgnoreCase("") || field.isEmpty());
    }
}
