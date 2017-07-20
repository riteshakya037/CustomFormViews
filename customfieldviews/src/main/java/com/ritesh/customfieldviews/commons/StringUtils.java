package com.ritesh.customfieldviews.commons;

/**
 * @author Ritesh Shakya
 */

public class StringUtils {
    public static boolean isNotNull(String field) {
        return !isNull(field);
    }

    private static boolean isNull(String field) {
        String tempField = field;
        if (tempField == null) {
            return true;
        } else {
            tempField = field.trim();
        }

        return (tempField.equalsIgnoreCase("NULL")
                || tempField.equalsIgnoreCase("")
                || tempField.isEmpty());
    }
}
