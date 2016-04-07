package saurabhkmr.teamselector.app.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by saurabhkmr on 27/3/16.
 */
public class Utils {

    public static String parseDate(String dateString){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat sdf2 = new SimpleDateFormat("MMM-dd HH:mm");
        String val=null;
        try {
            Date parse= sdf.parse(dateString);
            val=sdf2.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return val;
    }

    public static long getTime(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        long val=0;
        try {
            Date parse= sdf.parse(dateString);
            return parse.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return val;
    }
}
