package uk.mqchinee.lanterncore.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    private final TextUtils textUtils = new TextUtils();

    public long now(int offset) {
        Date date = new Date();
        date.setTime(date.getTime() + TimeUnit.HOURS.toMillis(offset));
        return date.getTime()/1000;
    }

    public String fromEpoch(long epoch, String format, String locale) {
        DateFormat frm = new SimpleDateFormat(format, Locale.forLanguageTag(locale));
        return frm.format(new Date(epoch*1000));
    }

}
