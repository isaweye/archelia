package uk.mqchinee.archelia.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Utility class for handling dates and times.
 */
public class DateUtils {

    /**
     * Get the current timestamp with an offset in hours.
     *
     * @param offset The offset in hours to apply to the current timestamp.
     * @return The current timestamp with the specified offset.
     */
    public long now(int offset) {
        Date date = new Date();
        date.setTime(date.getTime() + TimeUnit.HOURS.toMillis(offset));
        return date.getTime() / 1000;
    }

    /**
     * Convert a Unix epoch timestamp to a formatted date string.
     *
     * @param epoch The Unix epoch timestamp.
     * @param format The desired date format.
     * @param locale The locale for formatting the date.
     * @return The formatted date string.
     */
    public String fromEpoch(long epoch, String format, String locale) {
        DateFormat frm = new SimpleDateFormat(format, Locale.forLanguageTag(locale));
        return frm.format(new Date(epoch * 1000));
    }
}
