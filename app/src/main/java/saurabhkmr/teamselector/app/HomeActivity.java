package saurabhkmr.teamselector.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONObject;
import saurabhkmr.teamselector.app.Utils.Utils;
import saurabhkmr.teamselector.app.models.Matches;
import saurabhkmr.teamselector.app.requests.AsyncResponse;
import saurabhkmr.teamselector.app.requests.MyAsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saurabhkmr on 19/3/16.
 */
public class HomeActivity extends BaseActivity {

    List<Matches> matchesList;
    TextView countDownTxtView;
    //Match1
    TextView match1Squad1TextViewHome;
    TextView match1Squad2TextViewHome;
    TextView match1VsTextView;
    ImageView match1Squad1ImageView;
    ImageView match1Squad2ImageView;
    Button match1Button;

    //Match2
    TextView match2Squad1TextViewHome;
    TextView match2Squad2TextViewHome;
    TextView match2VsTextView;
    ImageView match2Squad1ImageView;
    ImageView match2Squad2ImageView;
    Button match2Button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        matchesList = new ArrayList<Matches>();
        countDownTxtView=(TextView)findViewById(R.id.countDown);

        //match1
        match1Squad1TextViewHome=(TextView)findViewById(R.id.match1Squad1TextViewHome);
        match1Squad2TextViewHome=(TextView)findViewById(R.id.match1Sqaud2TextViewHome);
        match1Squad1ImageView=(ImageView)findViewById(R.id.match1Squad1ImgHome);
        match1Squad2ImageView=(ImageView)findViewById(R.id.match1Squad2ImgHome);
        match1VsTextView=(TextView)findViewById(R.id.match1VsHome);
        match1Button=(Button)findViewById(R.id.btn_SelectTeam1);

        //match2
        match2Squad1TextViewHome=(TextView)findViewById(R.id.match2Squad1TextViewHome);
        match2Squad2TextViewHome=(TextView)findViewById(R.id.match2Sqaud2TextViewHome);
        match2Squad1ImageView=(ImageView)findViewById(R.id.match2Squad1ImgHome);
        match2Squad2ImageView=(ImageView)findViewById(R.id.match2Squad2ImgHome);
        match2VsTextView=(TextView)findViewById(R.id.match2VsHome);
        match2Button=(Button)findViewById(R.id.btn_SelectTeam2);

        getMatches();
    }

    public static String getDateHourMinSecond(long startTime) {

        long endTime=System.currentTimeMillis();

        long diff = startTime-endTime;
        Log.e("day", "miliday"+diff);
        long seconds =  (diff / 1000) % 60 ;
        Log.e("second", "miliday"+seconds);
        long minutes =  ((diff / (1000*60)) % 60);
        Log.e("minute", "miliday"+minutes);
        long hours   =  ((diff / (1000*60*60)) % 24);
        Log.e("hour", "miliday"+hours);
        long days = (int)((diff / (1000*60*60*24)) % 365);
        return days + " days : " + hours + " hours";
    }
    public void getMatches(){

        String url = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/matches?seriesName=ipl";

        MyAsyncTask asyncTask =new MyAsyncTask(new AsyncResponse() {

            @Override
            public void processFinish(Object output) {
                try {
                    if (output == null) {
                        Log.d("no internet", "no internet");
                        return;
                    }
                    JSONObject jsonObject = (JSONObject) output;
                    JSONArray matches = (JSONArray) jsonObject.get("matches");

                    for (int i = 0; i < matches.length(); i++) {
                        if (i == 0) {
                            long time = Utils.getTime(matches.getJSONObject(i).getString("date"));
                            String countDown = getDateHourMinSecond(time);
                            countDownTxtView.setText(countDown);
                            String squad1 = matches.getJSONObject(i).getString("homeTeam");
                            String squad2 = matches.getJSONObject(i).getString("awayTeam");
                            match1Squad1TextViewHome.setText(squad1);
                            match1Squad2TextViewHome.setText(squad2);
                            match1VsTextView.setText("vs");
                            setImage(match1Squad1ImageView, squad1);
                            setImage(match1Squad2ImageView, squad2);
                            match1Button.setVisibility(View.VISIBLE);
                        }
                        if (i == 1) {
                            String squad1 = matches.getJSONObject(i).getString("homeTeam");
                            String squad2 = matches.getJSONObject(i).getString("awayTeam");
                            match2Squad1TextViewHome.setText(squad1);
                            match2Squad2TextViewHome.setText(squad2);
                            match2VsTextView.setText("vs");
                            setImage(match2Squad1ImageView, squad1);
                            setImage(match2Squad2ImageView, squad2);
                            match2Button.setVisibility(View.VISIBLE);
                        }
                        Matches match = new Matches();
                        match.setAwayTeam(matches.getJSONObject(i).getString("awayTeam"));
                        match.setHomeTeam(matches.getJSONObject(i).getString("homeTeam"));
                        match.setVenue(matches.getJSONObject(i).getString("venue"));
                        match.setDate(Utils.parseDate(matches.getJSONObject(i).getString("date")));
                        matchesList.add(match);
                    }

                    Log.d(matches.getString(0), "matches");
                } catch (Exception ex) {
                    Log.d("something wrong happened", ex.getMessage());
                }
            }
        });
        asyncTask.execute(url,"GET");
    }



    public void setImage(ImageView imageView,String squadName){
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


    public void selectTeamMatch1(View view) {

    }


    public void selectTeamMatch2(View view) {
        
    }
}
