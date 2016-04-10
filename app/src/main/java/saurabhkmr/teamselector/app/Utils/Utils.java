package saurabhkmr.teamselector.app.Utils;

import android.widget.ImageView;
import saurabhkmr.teamselector.app.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by saurabhkmr on 27/3/16.
 */
public class Utils {

    private static long currentMatchId;

    public static long getCurrentMatchId() {
        return currentMatchId;
    }

    public static void setCurrentMatchId(long currentMatchId) {
        Utils.currentMatchId = currentMatchId;
    }

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

    public static  long getTimestamp(Date date){
        return date.getTime();
    }

    public static void setImage(ImageView imageView, String squadName){
        switch (squadName.toLowerCase()){
            case "mi":
                imageView.setImageResource(R.drawable.mi);
                break;
            case "dd":
                imageView.setImageResource(R.drawable.dd);
                break;
            case "rcb":
                imageView.setImageResource(R.drawable.rcb);
                break;
            case "srh":
                imageView.setImageResource(R.drawable.srh);
                break;
            case "kx1p":
                imageView.setImageResource(R.drawable.kx1p);
                break;
            case "kkr":
                imageView.setImageResource(R.drawable.kkr);
                break;
            case "gl":
                imageView.setImageResource(R.mipmap.gl);
                break;
            case "rps":
                imageView.setImageResource(R.mipmap.rps);
                break;
            default:break;
        }
    }
}
