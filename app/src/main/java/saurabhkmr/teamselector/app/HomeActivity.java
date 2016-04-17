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
import java.util.Date;
import java.util.List;

/**
 * Created by saurabhkmr on 19/3/16.
 */
public class HomeActivity extends BaseActivity {

    static List<Matches> matchesList=new ArrayList<>();
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
    long userId;
    TextView startsIn;
    static boolean matchInProgress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        countDownTxtView=(TextView)findViewById(R.id.countDown);
        startsIn=(TextView)findViewById(R.id.startsInTxtViewHome);

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
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        userId=pref.getLong("id",0);
        getMatches();
    }

    private void teamSelected1(long currMatchId) {
        try{

            String urlRps = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/" +
                    "team_select?user_id="+userId+"&match_id="+currMatchId;
            MyAsyncTask asyncTaskRps =new MyAsyncTask(new AsyncResponse() {

                @Override
                public void processFinish(Object output) {
                    List<Players> playersList=new ArrayList<>();
                    if(output==null){
                        return;
                    }
                    Log.d(output.toString(),"Response From Asynchronous task:");
                    try {
                        JSONObject jsonObject = (JSONObject) output;
                        JSONArray matches = (JSONArray) jsonObject.get("matchPoints");

                        for (int i=0; i<matches.length(); i++) {

                            Players players=new Players();
                            players.setName(matches.getJSONObject(i).getString("playerName"));
                            players.setId(matches.getJSONObject(i).getLong("id"));
                            players.setSquadId(matches.getJSONObject(i).getLong("squad"));
                            players.setCaptain(matches.getJSONObject(i).getBoolean("captain"));
                            players.setPoints(matches.getJSONObject(i).getLong("points"));
                            playersList.add(players);
                        }
                        long match1buttonTag= (long) match1Button.getTag();
                        if(match1buttonTag==matchId && playersList.size()>5){
                            match1Button.setText("Show Team");
                        }

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

    private void teamSelected2(long currMatchId) {
        try{

            String urlRps = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/" +
                    "team_select?user_id="+userId+"&match_id="+currMatchId;
            MyAsyncTask asyncTaskRps =new MyAsyncTask(new AsyncResponse() {

                @Override
                public void processFinish(Object output) {
                    List<Players> playersList=new ArrayList<>();
                    if(output==null){
                        return;
                    }
                    Log.d(output.toString(),"Response From Asynchronous task:");
                    try {
                        JSONObject jsonObject = (JSONObject) output;
                        JSONArray matches = (JSONArray) jsonObject.get("matchPoints");

                        for (int i=0; i<matches.length(); i++) {

                            Players players=new Players();
                            players.setName(matches.getJSONObject(i).getString("playerName"));
                            players.setId(matches.getJSONObject(i).getLong("id"));
                            players.setSquadId(matches.getJSONObject(i).getLong("squad"));
                            players.setCaptain(matches.getJSONObject(i).getBoolean("captain"));
                            players.setPoints(matches.getJSONObject(i).getLong("points"));
                            playersList.add(players);
                        }
                        long match2buttonTag= (long) match2Button.getTag();
                        if(match2buttonTag==matchId+1 && playersList.size()>5){
                            match2Button.setText("Show Team");
                        }


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

    public  String getDateHourMinSecond(long startTime,long matchId) {

        long endTime=Utils.getTimestamp(new Date());
        this.matchId=matchId;
        long diff = startTime-endTime;
        Log.e("day", "miliday"+diff);
        long seconds =  (diff / 1000) % 60 ;
        Log.e("second", "miliday"+seconds);
        long minutes =  ((diff / (1000*60)) % 60);
        Log.e("minute", "miliday"+minutes);
        long hours   =  ((diff / (1000*60*60)) % 24);
        Log.e("hour", "miliday"+hours);
        long days = (int)((diff / (1000*60*60*24)) % 365);
        if(hours<=0 && minutes <30){
            startsIn.setText("Match Started");
            Utils.setCurrentMatchId(matchId);
            return "";
        }
        else {
            startsIn.setText("Next match in");
            return days + " days : " + hours + " hours";
        }
    }
    public void getMatches(){

        String url = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/matches?seriesName=ipl&upcoming=true";

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
                            long id=matches.getJSONObject(i).getLong("id");
                            String countDown = getDateHourMinSecond(time,id);
                            countDownTxtView.setText(countDown);
                            String squad1 = matches.getJSONObject(i).getString("homeTeam");
                            String squad2 = matches.getJSONObject(i).getString("awayTeam");

                           // if(id==matchId){
                             //   match1Button.setText("Show Team");
                           // }
                            match1Squad1TextViewHome.setText(squad1);
                            match1Squad2TextViewHome.setText(squad2);
                            match1VsTextView.setText("vs");
                            Utils.setImage(match1Squad1ImageView, squad1);
                            Utils.setImage(match1Squad2ImageView, squad2);
                            match1Button.setVisibility(View.VISIBLE);
                            match1Button.setTag(id);
                            teamSelected1(id);
                        }
                        if (i == 1) {
                            long id=matches.getJSONObject(i).getLong("id");
                            String squad1 = matches.getJSONObject(i).getString("homeTeam");
                            String squad2 = matches.getJSONObject(i).getString("awayTeam");
                            match2Squad1TextViewHome.setText(squad1);
                            match2Squad2TextViewHome.setText(squad2);
                            match2VsTextView.setText("vs");
                            Utils.setImage(match2Squad1ImageView, squad1);
                            Utils.setImage(match2Squad2ImageView, squad2);
                          //  if(id==matchId){
                            //    match2Button.setText("Show Team");
                          //  }
                            match2Button.setVisibility(View.VISIBLE);
                            match2Button.setTag(id);
                            teamSelected2(id);
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
            if(match1Button.getText()=="Show Team"){
                long currentMatchId= (long) match1Button.getTag();
                showSelectedPlayers(currentMatchId);
            }else {
                Matches match = matchesList.get(0);
                selectTeam(match.getId());
            }
        }
        catch (Exception ex){
            Log.d("something wrong happened", ex.getMessage());
        }
    }

    public void selectTeamMatch2(View view) {
        try {
            if (match2Button.getText() == "Show Team") {
                long currentMatchId= (long) match2Button.getTag();
                showSelectedPlayers(currentMatchId);

            } else {
                Matches match = matchesList.get(1);
                selectTeam(match.getId());
            }
        }
        catch (Exception ex){
            Log.d("something wrong happened", ex.getMessage());
        }
    }

    public static Matches getMatchById(long matchId){
        if(matchesList==null ){
            return null;
        }
        else{
            for(Matches matches:matchesList){
                if(matches.getId()==matchId){
                    return matches;
                }
            }
        }
        return null;
    }

    public void showSelectedPlayers(long currentMatchId){

        String urlRps = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/" +
                "team_select?user_id="+userId+"&match_id="+currentMatchId;
        MyAsyncTask asyncTaskRps =new MyAsyncTask(new AsyncResponse() {

            @Override
            public void processFinish(Object output) {
                List<Players> playersList=new ArrayList<>();
                if(output==null){
                    return;
                }
                Log.d(output.toString(),"Response From Asynchronous task:");
                try {
                    JSONObject jsonObject = (JSONObject) output;
                    JSONArray matches = (JSONArray) jsonObject.get("matchPoints");
                    if(matches.length()==0){
                        return;
                    }

                    for (int i=0; i<matches.length(); i++) {

                        Players players=new Players();
                        players.setName(matches.getJSONObject(i).getString("playerName"));
                        players.setId(matches.getJSONObject(i).getLong("id"));
                        players.setSquadId(matches.getJSONObject(i).getLong("squad"));
                        players.setCaptain(matches.getJSONObject(i).getBoolean("captain"));
                        players.setPoints(matches.getJSONObject(i).getLong("points"));
                        players.setMatchId(matches.getJSONObject(i).getLong("matchId"));
                        playersList.add(players);
                    }

                    Intent intent = new Intent(HomeActivity.this, CurrentMatchActivity.class);
                    intent.putExtra("players",(Serializable)playersList);
                    startActivity(intent);

                    Log.d(matches.getString(0),"players");
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
