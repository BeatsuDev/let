package no.ntnu.let.letapi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for dates
 */
public class DateUtil {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    /**
     * Get the current time
     * @return The current date
     */
    public static Date getNow() {
        return new java.util.Date();
    }

    /**
     * Parse a date using the format yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     * @param date The date to parse
     * @return The parsed date
     * @throws ParseException If the date could not be parsed
     */
    public static Date parseDate(String date) throws ParseException {
        return dateFormat.parse(date);
    }

    /**
     * Format a date using the format yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     * @param date The date to format
     * @return The formatted date
     */
    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }
}
