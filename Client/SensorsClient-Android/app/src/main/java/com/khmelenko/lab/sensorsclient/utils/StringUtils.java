package com.khmelenko.lab.sensorsclient.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Utilities for strings
 *
 * @author Dmytro Khmelenko (d.khmelenko@gmail.com)
 */
public final class StringUtils {

    private StringUtils() {

    }

    /**
     * Formats the decimal input
     *
     * @param input          Input
     * @param fractionDigits Fraction digits
     * @return Formatted input value
     */
    public static String formatDecimalDigits(String input, int fractionDigits) {
        String formatted = input;
        try {
            double inputValue = Double.valueOf(input);
            formatted = formatDecimalDigits(inputValue, fractionDigits);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return formatted;
    }

    /**
     * Formats the decimal input
     *
     * @param inputValue     Input double value
     * @param fractionDigits Fraction digits
     * @return Formatted input value
     */
    public static String formatDecimalDigits(double inputValue, int fractionDigits) {
        NumberFormat df = DecimalFormat.getInstance();
        df.setMinimumFractionDigits(1);
        df.setMaximumFractionDigits(fractionDigits);

        return df.format(inputValue);
    }

}
