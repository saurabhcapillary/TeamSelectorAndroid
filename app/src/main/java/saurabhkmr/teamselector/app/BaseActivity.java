package saurabhkmr.teamselector.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;
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
 * Created by saurabhkmr on 27/3/16.
 */
public class BaseActivity extends Activity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.popup, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(BaseActivity.this,
                "Clicked popup menu item " + item.getTitle(),
                Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.profile:
                Log.d("profile","profile");
                Intent profileIntent = new Intent(BaseActivity.this, ProfileActivity.class);
                startActivity(profileIntent);
                return true;
            case R.id.home:
                Log.d("home","home");
                Intent homeIntent = new Intent(BaseActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                return true;
            case R.id.about:
                Log.d("about","about");
                Intent aboutIntent = new Intent(BaseActivity.this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
            case R.id.leaderboard:
                Log.d("leaderboard","leaderboard");
                Intent leaderBoardItemt = new Intent(BaseActivity.this, LeaderboardActivity.class);
                startActivity(leaderBoardItemt);
                return true;
            case R.id.signOut:
                Log.d("signOut","signOut");
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                finish();
                Intent loginIntent = new Intent(BaseActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                return true;
            case R.id.matches:
                Log.d("matches","matches");

                String url = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/matches?seriesName=ipl";
                MyAsyncTask asyncTask =new MyAsyncTask(new AsyncResponse() {

                    List<Matches> matchesList=new ArrayList<Matches>();
                    @Override
                    public void processFinish(Object output) {
                        if(output==null){
                            return;
                        }
                        Log.d(output.toString(),"Response From Asynchronous task:");
                        try {
                            JSONObject jsonObject = (JSONObject) output;
                            JSONArray matches = (JSONArray) jsonObject.get("matches");

                            for (int i=0; i<matches.length(); i++) {

                                Matches match=new Matches();
                                match.setAwayTeam(matches.getJSONObject(i).getString("awayTeam"));
                                match.setHomeTeam(matches.getJSONObject(i).getString("homeTeam"));
                                match.setVenue(matches.getJSONObject(i).getString("venue"));
                                match.setDate(Utils.parseDate(matches.getJSONObject(i).getString("date")));
                                matchesList.add(match);
                            }

                            Intent intent = new Intent(BaseActivity.this, MatchesActivity.class);
                            intent.putExtra("matches",(Serializable)matchesList);
                            startActivity(intent);

                            Log.d(matches.getString(0),"matches");
                        }
                        catch (Exception ex){

                        }
                    }
                });
                asyncTask.execute(new Object[] { url,"GET"});
                return true;
            case R.id.kkr:
                Log.d("kkr","kkr");

                String urlKkr = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/players/1";
                MyAsyncTask asyncTaskKkr =new MyAsyncTask(new AsyncResponse() {

                    List<Players> kkrPlayersList=new ArrayList<Players>();
                    @Override
                    public void processFinish(Object output) {
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
                                players.setId(1);
                                kkrPlayersList.add(players);
                            }

                            Intent kkrIntent = new Intent(BaseActivity.this, SquadsActivity.class);
                            kkrIntent.putExtra("players",(Serializable)kkrPlayersList);
                            startActivity(kkrIntent);
                            Log.d(matches.getString(0),"players");
                        }
                        catch (Exception ex){
                            Log.d("squads",ex.getMessage());
                        }
                    }
                });
                asyncTaskKkr.execute(new Object[] { urlKkr,"GET"});
                return true;
            case R.id.srh:
                Log.d("squads","squads");
                String urlSrh = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/players/2";
                MyAsyncTask asyncTaskSrh =new MyAsyncTask(new AsyncResponse() {

                    List<Players> srhPlayersList=new ArrayList<Players>();
                    @Override
                    public void processFinish(Object output) {
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
                                players.setId(2);
                                srhPlayersList.add(players);
                            }

                            Intent srhIntent = new Intent(BaseActivity.this, SquadsActivity.class);
                            srhIntent.putExtra("players",(Serializable)srhPlayersList);
                            startActivity(srhIntent);

                            Log.d(matches.getString(0),"players");
                        }
                        catch (Exception ex){

                        }
                    }
                });
                asyncTaskSrh.execute(new Object[] { urlSrh,"GET"});
                return true;
            case R.id.rcb:
                Log.d("squads","squads");
                String urlRcb = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/players/3";
                MyAsyncTask asyncTaskRcb =new MyAsyncTask(new AsyncResponse() {

                    List<Players> kkrPlayersList=new ArrayList<Players>();
                    @Override
                    public void processFinish(Object output) {
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
                                players.setId(3);
                                kkrPlayersList.add(players);
                            }

                            Intent rcbIntent = new Intent(BaseActivity.this, SquadsActivity.class);
                            rcbIntent.putExtra("players",(Serializable)kkrPlayersList);
                            startActivity(rcbIntent);
                            Log.d(matches.getString(0),"players");
                        }
                        catch (Exception ex){
                            Log.d("squads",ex.getMessage());
                        }
                    }
                });
                asyncTaskRcb.execute(new Object[] { urlRcb,"GET"});
                return true;
            case R.id.dd:
                Log.d("squads","squads");
                String urlDd = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/players/4";
                MyAsyncTask asyncTaskDd =new MyAsyncTask(new AsyncResponse() {

                    List<Players> ddPlayersList=new ArrayList<Players>();
                    @Override
                    public void processFinish(Object output) {
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
                                players.setId(4);
                                ddPlayersList.add(players);
                            }

                            Intent intent = new Intent(BaseActivity.this,SquadsActivity.class);
                            intent.putExtra("players",(Serializable)ddPlayersList);
                            startActivity(intent);

                            Log.d(matches.getString(0),"players");
                        }
                        catch (Exception ex){

                        }
                    }
                });
                asyncTaskDd.execute(new Object[] { urlDd,"GET"});
                return true;
            case R.id.mi:
                Log.d("squads","squads");
                String urlMi = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/players/6";
                MyAsyncTask asyncTaskMi =new MyAsyncTask(new AsyncResponse() {

                    List<Players> srhPlayersList=new ArrayList<Players>();
                    @Override
                    public void processFinish(Object output) {
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
                                players.setId(6);
                                srhPlayersList.add(players);
                            }

                            Intent intent = new Intent(BaseActivity.this, SquadsActivity.class);
                            intent.putExtra("players",(Serializable)srhPlayersList);
                            startActivity(intent);

                            Log.d(matches.getString(0),"players");
                        }
                        catch (Exception ex){

                        }
                    }
                });
                asyncTaskMi.execute(new Object[] { urlMi,"GET"});
                return true;
            case R.id.kx1p:
                Log.d("squads","squads");
                String urlKX1P = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/players/5";
                MyAsyncTask asyncTaskKx1p =new MyAsyncTask(new AsyncResponse() {

                    List<Players> srhPlayersList=new ArrayList<Players>();
                    @Override
                    public void processFinish(Object output) {
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
                                players.setId(5);
                                srhPlayersList.add(players);
                            }

                            Intent intent = new Intent(BaseActivity.this, SquadsActivity.class);
                            intent.putExtra("players",(Serializable)srhPlayersList);
                            startActivity(intent);

                            Log.d(matches.getString(0),"players");
                        }
                        catch (Exception ex){

                        }
                    }
                });
                asyncTaskKx1p.execute(new Object[] { urlKX1P,"GET"});
                return true;
            case R.id.gl:
                Log.d("squads","squads");
                String urlGl = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/players/7";
                MyAsyncTask asyncTaskGl =new MyAsyncTask(new AsyncResponse() {

                    List<Players> srhPlayersList=new ArrayList<Players>();
                    @Override
                    public void processFinish(Object output) {
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
                                players.setId(7);
                                srhPlayersList.add(players);
                            }

                            Intent intent = new Intent(BaseActivity.this, SquadsActivity.class);
                            intent.putExtra("players",(Serializable)srhPlayersList);
                            startActivity(intent);

                            Log.d(matches.getString(0),"players");
                        }
                        catch (Exception ex){

                        }
                    }
                });
                asyncTaskGl.execute(new Object[] { urlGl,"GET"});
                return true;
            case R.id.rps:
                Log.d("rps","rps");
                String urlRps = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/players/8";
                MyAsyncTask asyncTaskRps =new MyAsyncTask(new AsyncResponse() {

                    List<Players> rpsPlayersList=new ArrayList<Players>();
                    @Override
                    public void processFinish(Object output) {
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
                                players.setId(8);
                                rpsPlayersList.add(players);
                            }

                            Intent intent = new Intent(BaseActivity.this, SquadsActivity.class);
                            intent.putExtra("players",(Serializable)rpsPlayersList);
                            startActivity(intent);

                            Log.d(matches.getString(0),"players");
                        }
                        catch (Exception ex){

                        }
                    }
                });
                asyncTaskRps.execute(new Object[] { urlRps,"GET"});
                return true;
        }
        return false;
    }

    public void showPopup(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(BaseActivity.this,
                        "Clicked popup menu item " + item.getTitle(),
                        Toast.LENGTH_SHORT).show();
                switch (item.getItemId()) {
                    case R.id.profile:
                        Log.d("profile","profile");
                        return true;
                    case R.id.home:
                        Log.d("home","home");
                        Intent homeIntent = new Intent(BaseActivity.this, HomeActivity.class);
                        startActivity(homeIntent);
                        return true;
                    case R.id.matches:
                        Log.d("matches","matches");

                        String url = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/matches?seriesName=ipl";
                        MyAsyncTask asyncTask =new MyAsyncTask(new AsyncResponse() {

                            List<Matches> matchesList=new ArrayList<Matches>();
                            @Override
                            public void processFinish(Object output) {
                                Log.d(output.toString(),"Response From Asynchronous task:");
                                try {
                                    JSONObject jsonObject = (JSONObject) output;
                                    JSONArray matches = (JSONArray) jsonObject.get("matches");

                                    for (int i=0; i<matches.length(); i++) {

                                        Matches match=new Matches();
                                        match.setAwayTeam(matches.getJSONObject(i).getString("awayTeam"));
                                        match.setHomeTeam(matches.getJSONObject(i).getString("homeTeam"));
                                        match.setVenue(matches.getJSONObject(i).getString("venue"));
                                        match.setDate(Utils.parseDate(matches.getJSONObject(i).getString("date")));
                                        matchesList.add(match);
                                    }

                                    Intent intent = new Intent(BaseActivity.this, MatchesActivity.class);
                                    intent.putExtra("matches",(Serializable)matchesList);
                                    startActivity(intent);

                                    Log.d(matches.getString(0),"matches");
                                }
                                catch (Exception ex){

                                }
                            }
                        });
                        asyncTask.execute(new Object[] { url,"GET"});
                        return true;
                    case R.id.kkr:
                        Log.d("kkr","kkr");

                        String urlKkr = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/players/1";
                        MyAsyncTask asyncTaskKkr =new MyAsyncTask(new AsyncResponse() {

                            List<Players> kkrPlayersList=new ArrayList<Players>();
                            @Override
                            public void processFinish(Object output) {
                                Log.d(output.toString(),"Response From Asynchronous task:");
                                try {
                                    JSONObject jsonObject = (JSONObject) output;
                                    JSONArray matches = (JSONArray) jsonObject.get("players");

                                    for (int i=0; i<matches.length(); i++) {

                                        Players players=new Players();
                                        players.setName(matches.getJSONObject(i).getString("name"));
                                        players.setCountryName(matches.getJSONObject(i).getString("countryName"));
                                        players.setId(1);
                                        kkrPlayersList.add(players);
                                    }

                                    Intent kkrIntent = new Intent(BaseActivity.this, SquadsActivity.class);
                                    kkrIntent.putExtra("players",(Serializable)kkrPlayersList);
                                    startActivity(kkrIntent);
                                    Log.d(matches.getString(0),"players");
                                }
                                catch (Exception ex){
                                    Log.d("squads",ex.getMessage());
                                }
                            }
                        });
                        asyncTaskKkr.execute(new Object[] { urlKkr,"GET"});
                        return true;
                    case R.id.srh:
                        Log.d("squads","squads");
                        String urlSrh = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/players/2";
                        MyAsyncTask asyncTaskSrh =new MyAsyncTask(new AsyncResponse() {

                            List<Players> srhPlayersList=new ArrayList<Players>();
                            @Override
                            public void processFinish(Object output) {
                                Log.d(output.toString(),"Response From Asynchronous task:");
                                try {
                                    JSONObject jsonObject = (JSONObject) output;
                                    JSONArray matches = (JSONArray) jsonObject.get("players");

                                    for (int i=0; i<matches.length(); i++) {

                                        Players players=new Players();
                                        players.setName(matches.getJSONObject(i).getString("name"));
                                        players.setCountryName(matches.getJSONObject(i).getString("countryName"));
                                        players.setId(2);
                                        srhPlayersList.add(players);
                                    }

                                    Intent srhIntent = new Intent(BaseActivity.this, SquadsActivity.class);
                                    srhIntent.putExtra("players",(Serializable)srhPlayersList);
                                    startActivity(srhIntent);

                                    Log.d(matches.getString(0),"players");
                                }
                                catch (Exception ex){

                                }
                            }
                        });
                        asyncTaskSrh.execute(new Object[] { urlSrh,"GET"});
                        return true;
                    case R.id.rcb:
                        Log.d("squads","squads");
                        String urlRcb = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/players/3";
                        MyAsyncTask asyncTaskRcb =new MyAsyncTask(new AsyncResponse() {

                            List<Players> kkrPlayersList=new ArrayList<Players>();
                            @Override
                            public void processFinish(Object output) {
                                Log.d(output.toString(),"Response From Asynchronous task:");
                                try {
                                    JSONObject jsonObject = (JSONObject) output;
                                    JSONArray matches = (JSONArray) jsonObject.get("players");

                                    for (int i=0; i<matches.length(); i++) {

                                        Players players=new Players();
                                        players.setName(matches.getJSONObject(i).getString("name"));
                                        players.setCountryName(matches.getJSONObject(i).getString("countryName"));
                                        players.setId(3);
                                        kkrPlayersList.add(players);
                                    }

                                    Intent rcbIntent = new Intent(BaseActivity.this, SquadsActivity.class);
                                    rcbIntent.putExtra("players",(Serializable)kkrPlayersList);
                                    startActivity(rcbIntent);
                                    Log.d(matches.getString(0),"players");
                                }
                                catch (Exception ex){
                                    Log.d("squads",ex.getMessage());
                                }
                            }
                        });
                        asyncTaskRcb.execute(new Object[] { urlRcb,"GET"});
                        return true;
                    case R.id.dd:
                        Log.d("squads","squads");
                        String urlDd = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/players/4";
                        MyAsyncTask asyncTaskDd =new MyAsyncTask(new AsyncResponse() {

                            List<Players> ddPlayersList=new ArrayList<Players>();
                            @Override
                            public void processFinish(Object output) {
                                Log.d(output.toString(),"Response From Asynchronous task:");
                                try {
                                    JSONObject jsonObject = (JSONObject) output;
                                    JSONArray matches = (JSONArray) jsonObject.get("players");

                                    for (int i=0; i<matches.length(); i++) {

                                        Players players=new Players();
                                        players.setName(matches.getJSONObject(i).getString("name"));
                                        players.setCountryName(matches.getJSONObject(i).getString("countryName"));
                                        players.setId(4);
                                        ddPlayersList.add(players);
                                    }

                                    Intent intent = new Intent(BaseActivity.this,SquadsActivity.class);
                                    intent.putExtra("players",(Serializable)ddPlayersList);
                                    startActivity(intent);

                                    Log.d(matches.getString(0),"players");
                                }
                                catch (Exception ex){

                                }
                            }
                        });
                        asyncTaskDd.execute(new Object[] { urlDd,"GET"});
                        return true;
                    case R.id.mi:
                        Log.d("squads","squads");
                        String urlMi = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/players/6";
                        MyAsyncTask asyncTaskMi =new MyAsyncTask(new AsyncResponse() {

                            List<Players> srhPlayersList=new ArrayList<Players>();
                            @Override
                            public void processFinish(Object output) {
                                Log.d(output.toString(),"Response From Asynchronous task:");
                                try {
                                    JSONObject jsonObject = (JSONObject) output;
                                    JSONArray matches = (JSONArray) jsonObject.get("players");

                                    for (int i=0; i<matches.length(); i++) {

                                        Players players=new Players();
                                        players.setName(matches.getJSONObject(i).getString("name"));
                                        players.setCountryName(matches.getJSONObject(i).getString("countryName"));
                                        players.setId(6);
                                        srhPlayersList.add(players);
                                    }

                                    Intent intent = new Intent(BaseActivity.this, SquadsActivity.class);
                                    intent.putExtra("players",(Serializable)srhPlayersList);
                                    startActivity(intent);

                                    Log.d(matches.getString(0),"players");
                                }
                                catch (Exception ex){

                                }
                            }
                        });
                        asyncTaskMi.execute(new Object[] { urlMi,"GET"});
                        return true;
                    case R.id.kx1p:
                        Log.d("squads","squads");
                        String urlKX1P = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/players/5";
                        MyAsyncTask asyncTaskKx1p =new MyAsyncTask(new AsyncResponse() {

                            List<Players> srhPlayersList=new ArrayList<Players>();
                            @Override
                            public void processFinish(Object output) {
                                Log.d(output.toString(),"Response From Asynchronous task:");
                                try {
                                    JSONObject jsonObject = (JSONObject) output;
                                    JSONArray matches = (JSONArray) jsonObject.get("players");

                                    for (int i=0; i<matches.length(); i++) {

                                        Players players=new Players();
                                        players.setName(matches.getJSONObject(i).getString("name"));
                                        players.setCountryName(matches.getJSONObject(i).getString("countryName"));
                                        players.setId(5);
                                        srhPlayersList.add(players);
                                    }

                                    Intent intent = new Intent(BaseActivity.this, SquadsActivity.class);
                                    intent.putExtra("players",(Serializable)srhPlayersList);
                                    startActivity(intent);

                                    Log.d(matches.getString(0),"players");
                                }
                                catch (Exception ex){

                                }
                            }
                        });
                        asyncTaskKx1p.execute(new Object[] { urlKX1P,"GET"});
                        return true;
                    case R.id.gl:
                        Log.d("squads","squads");
                        String urlGl = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/players/7";
                        MyAsyncTask asyncTaskGl =new MyAsyncTask(new AsyncResponse() {

                            List<Players> srhPlayersList=new ArrayList<Players>();
                            @Override
                            public void processFinish(Object output) {
                                Log.d(output.toString(),"Response From Asynchronous task:");
                                try {
                                    JSONObject jsonObject = (JSONObject) output;
                                    JSONArray matches = (JSONArray) jsonObject.get("players");

                                    for (int i=0; i<matches.length(); i++) {

                                        Players players=new Players();
                                        players.setName(matches.getJSONObject(i).getString("name"));
                                        players.setCountryName(matches.getJSONObject(i).getString("countryName"));
                                        players.setId(7);
                                        srhPlayersList.add(players);
                                    }

                                    Intent intent = new Intent(BaseActivity.this, SquadsActivity.class);
                                    intent.putExtra("players",(Serializable)srhPlayersList);
                                    startActivity(intent);

                                    Log.d(matches.getString(0),"players");
                                }
                                catch (Exception ex){

                                }
                            }
                        });
                        asyncTaskGl.execute(new Object[] { urlGl,"GET"});
                        return true;
                    case R.id.rps:
                        Log.d("rps","rps");
                        String urlRps = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/players/8";
                        MyAsyncTask asyncTaskRps =new MyAsyncTask(new AsyncResponse() {

                            List<Players> rpsPlayersList=new ArrayList<Players>();
                            @Override
                            public void processFinish(Object output) {
                                Log.d(output.toString(),"Response From Asynchronous task:");
                                try {
                                    JSONObject jsonObject = (JSONObject) output;
                                    JSONArray matches = (JSONArray) jsonObject.get("players");

                                    for (int i=0; i<matches.length(); i++) {

                                        Players players=new Players();
                                        players.setName(matches.getJSONObject(i).getString("name"));
                                        players.setCountryName(matches.getJSONObject(i).getString("countryName"));
                                        players.setId(8);
                                        rpsPlayersList.add(players);
                                    }

                                    Intent intent = new Intent(BaseActivity.this, SquadsActivity.class);
                                    intent.putExtra("players",(Serializable)rpsPlayersList);
                                    startActivity(intent);

                                    Log.d(matches.getString(0),"players");
                                }
                                catch (Exception ex){

                                }
                            }
                        });
                        asyncTaskRps.execute(new Object[] { urlRps,"GET"});
                        return true;
                }
                return false;
            }
        });

        popup.show();
    }




}

