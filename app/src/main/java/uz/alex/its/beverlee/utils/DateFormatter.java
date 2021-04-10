package uz.alex.its.beverlee.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter {
    public static String timestampToStringDate(final long timestamp) {
        final Date date = new Date(timestamp * 1000);
        return dateFormat.format(date);
    }

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
    private static final String TAG = DateFormatter.class.toString();
}
