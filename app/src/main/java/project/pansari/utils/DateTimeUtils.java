package project.pansari.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {

    public static String getDate(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date(millis);
        return sdf.format(date);
    }

    public static String getTime(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        Date date = new Date(millis);
        return sdf.format(date);
    }
}
