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
import saurabhkmr.teamselector.app.models.Players;
import saurabhkmr.teamselector.app.requests.AsyncResponse;
import saurabhkmr.teamselector.app.requests.MyAsyncTask;

import java.io.Serializable;
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
    long matchId;

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

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        matchId=pref.getLong("matchid",0);
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
                            long id=matches.getJSONObject(i).getLong("id");
                            if(id==matchId){
                                match1Button.setEnabled(false);
                                match1Button.setText("Done");
                            }
                            match1Squad1TextViewHome.setText(squad1);
                            match1Squad2TextViewHome.setText(squad2);
                            match1VsTextView.setText("vs");
                            Utils.setImage(match1Squad1ImageView, squad1);
                            Utils.setImage(match1Squad2ImageView, squad2);
                            match1Button.setVisibility(View.VISIBLE);
                        }
                        if (i == 1) {
                            String squad1 = matches.getJSONObject(i).getString("homeTeam");
                            String squad2 = matches.getJSONObject(i).getString("awayTeam");
                            match2Squad1TextViewHome.setText(squad1);
                            match2Squad2TextViewHome.setText(squad2);
                            match2VsTextView.setText("vs");
                            Utils.setImage(match2Squad1ImageView, squad1);
                            Utils.setImage(match2Squad2ImageView, squad2);
                            match2Button.setVisibility(View.VISIBLE);
                        }
                        Matches match = new Matches();
                        match.setAwayTeam(matches.getJSONObject(i).getString("awayTeam"));
                        match.setId(matches.getJSONObject(i).getLong("id"));
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



    public void selectTeamMatch1(View view) {
        try {
            Matches match = matchesList.get(0);
            selectTeam(view,match);
        }
        catch (Exception ex){
            Log.d("something wrong happened", ex.getMessage());
        }

    }


    public void selectTeamMatch2(View view) {
        try {
            Matches match = matchesList.get(1);
            selectTeam(view,match);
        }
        catch (Exception ex){
            Log.d("something wrong happened", ex.getMessage());
        }
    }

    public void selectTeam(View view, final Matches currentMatch){

        final long matchId= (int) currentMatch.getId();
        final int homeTeamId = Matches.getId(currentMatch.getHomeTeam());
        final int awayTeamId = Matches.getId(currentMatch.getAwayTeam());

        String urlRps = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/players/"+homeTeamId;
        MyAsyncTask asyncTaskRps =new MyAsyncTask(new AsyncResponse() {

            List<Players> playersList=new ArrayList<Players>();
            @Override
            public void processFinish(Object output) {
                if(output==null){
                    return;
                }
                Log.d(output.toString(),"Response From Asynchronous task:");
                try {
                    JSONObject jsonObject = (JSONObject) output;
                    JSONArray playersJson = (JSONArray) jsonObject.get("players");

                    for (int i=0; i<playersJson.length(); i++) {

                        Players players=new Players();
                        players.setName(playersJson.getJSONObject(i).getString("name"));
                        players.setCountryName(playersJson.getJSONObject(i).getString("countryName"));
                        players.setId(playersJson.getJSONObject(i).getLong("id"));
                        players.setMatchId(matchId);
                        players.setSquadName(currentMatch.getHomeTeam());
                        players.setHomeSquad(true);
                        playersList.add(players);
                    }

                    String urlRps = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/players/"+awayTeamId;
                    MyAsyncTask asyncTaskRps =new MyAsyncTask(new AsyncResponse() {

                        @Override
                        public void processFinish(Object output) {
                            List<Players> squad2Players=new ArrayList<>();
                            if(output==null){
                                return;
                            }
                            Log.d(output.toString(),"Response From Asynchronous task:");
                            try {
                                JSONObject jsonObject = (JSONObject) output;
                                JSONArray matches = (JSONArray) jsonObject.get("players");

                                for (int i=0; i<matches.length(); i++) {

                                    Players players=new Players();
                                    players.setName(matches.getJSONObject(i).getString("name"));
                                    players.setCountryName(matches.getJSONObject(i).getString("countryName"));
                                    players.setId(matches.getJSONObject(i).getLong("id"));
                                    players.setMatchId(matchId);
                                    players.setSquadName(currentMatch.getAwayTeam());
                                    squad2Players.add(players);
                                }

                                Intent intent = new Intent(HomeActivity.this, PickTeamActivity.class);
                                intent.putExtra("squad1Players",(Serializable)playersList);
                                intent.putExtra("squad2Players",(Serializable)squad2Players);
                                startActivity(intent);

                                Log.d(matches.getString(0),"players");
                            }
                            catch (Exception ex){

                            }
                        }
                    });
                    asyncTaskRps.execute(new Object[] { urlRps,"GET"});

                }
                catch (Exception ex){

                }
            }
        });
        asyncTaskRps.execute(new Object[] { urlRps,"GET"});


    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
