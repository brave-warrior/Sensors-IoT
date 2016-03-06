package com.khmelenko.lab.sensorsclient.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Provides utils methods for processing data & time
 *
 * @author Dmytro Khmelenko
 */
public final class DateTimeUtils {

    private DateTimeUtils() {
    }

    /**
     * Formats date time structure to the local string
     *
     * @param dateTime Datetime
     * @return Formatted date time in local
     */
    public static String formatDateTimeLocal(Date dateTime) {
        DateFormat formatter = SimpleDateFormat.getDateTimeInstance();
        String formatted = formatter.format(dateTime);
        return formatted;
    }

    /**
     * Parses XML formatted date/time
     *
     * @param xmlDateTime XML formatted date/time
     * @return Parsed datetime
     * @throws ParseException Throws on parsing error
     */
    public static Date parseXmlDateTime(String xmlDateTime) throws ParseException {
        xmlDateTime = xmlDateTime.replace("Z", "+0000");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault());
        Date parsedDate = dateFormat.parse(xmlDateTime);
        return parsedDate;
    }

    /**
     * Parses and formats xml date time string
     *
     * @param xmlDateTime XML date time
     * @return Formatted date time
     */
    public static String parseAndFormatDateTime(String xmlDateTime) {
        String formattedDate = xmlDateTime;
        try {
            Date finishedAt = DateTimeUtils.parseXmlDateTime(xmlDateTime);
            formattedDate = DateTimeUtils.formatDateTimeLocal(finishedAt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

}

