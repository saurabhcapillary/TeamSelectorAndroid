package saurabhkmr.teamselector.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONObject;
import saurabhkmr.teamselector.app.Utils.Utils;
import saurabhkmr.teamselector.app.adapter.CurrentMatchAdapter;
import saurabhkmr.teamselector.app.models.Matches;
import saurabhkmr.teamselector.app.models.Players;
import saurabhkmr.teamselector.app.requests.AsyncResponse;
import saurabhkmr.teamselector.app.requests.MyAsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saurabhkmr on 10/4/16 @Capillary Tech
 */
public class CurrentMatchActivity extends BaseActivity {
    ListView lv;
    Context context;
    LinearLayout linearLayout;
    Button editSquadButton;
    long matchId;
    long userId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_team);

        context=this;
        long currentMatchId = Utils.getCurrentMatchId();
        lv=(ListView) findViewById(R.id.currentPlayersListView);
        editSquadButton=(Button)findViewById(R.id.btn_editSquad);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        userId=pref.getLong("id",0);

        Bundle extras = getIntent().getExtras();
        List<Players> players = (ArrayList<Players>) extras.getSerializable("players");
        matchId=players.get(0).getMatchId();
        if(matchId==currentMatchId){
            editSquadButton.setText("Team Locked");
            editSquadButton.setEnabled(false);
        }
        lv.setAdapter(new CurrentMatchAdapter(CurrentMatchActivity.this,players));
    }

    public void editSquadClick(View view) {
        resetAndSelectTeam();

    }

    private void resetAndSelectTeam() {
        try{
            String urlRps = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/" +
                    "team_select?user_id="+userId+"&match_id="+matchId;
            MyAsyncTask asyncTaskRps =new MyAsyncTask(new AsyncResponse() {

                @Override
                public void processFinish(Object output) {
                    if(output==null){
                        return;
                    }
                    Log.d(output.toString(),"Response From Asynchronous task:");
                    try {
                        JSONObject jsonObject = (JSONObject) output;
                        if(jsonObject.toString().contains("200")) {
                            selectTeam(matchId);
                        }
                    }
                    catch (Exception ex){

                    }
                }
            });
            asyncTaskRps.execute(new Object[] { urlRps,"DELETE"});

        }
        catch (Exception ex){

        }
    }
}
