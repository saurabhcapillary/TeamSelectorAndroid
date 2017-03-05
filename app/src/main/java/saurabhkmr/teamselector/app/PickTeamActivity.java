package saurabhkmr.teamselector.app;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;
import saurabhkmr.teamselector.app.Utils.Utils;
import saurabhkmr.teamselector.app.adapter.CheckBoxListviewAdapter;
import saurabhkmr.teamselector.app.models.Players;
import saurabhkmr.teamselector.app.requests.AsyncResponse;
import saurabhkmr.teamselector.app.requests.MyAsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saurabhkmr on 7/4/16 @Capillary Tech
 */
public class PickTeamActivity extends BaseActivity {

    ListView lv;
    Context context;
    ImageView squad1Img;
    ImageView squad2Img;
    TextView errorMessageTextView;
    long userId;
    long matchId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_team);
        context = this;
        lv = (ListView) findViewById(R.id.pick_PlayersListView);
        squad1Img=(ImageView)findViewById(R.id.tp_squad1Img);
        squad2Img=(ImageView)findViewById(R.id.tp_squad2Img);
        errorMessageTextView=(TextView)findViewById(R.id.error_messagePickTeam);
        Bundle extras = getIntent().getExtras();
        List<Players> squad1Players = (ArrayList<Players>) extras.getSerializable("squad1Players");
        List<Players> squad2Players = (ArrayList<Players>) extras.getSerializable("squad2Players");
        Utils.setImage(squad1Img,squad1Players.get(0).getSquadName() );
        Utils.setImage(squad2Img, squad2Players.get(0).getSquadName());
        if(squad1Players!=null && squad1Players.size()>0 && squad2Players!=null && squad2Players.size()>0) {
            lv.setAdapter(new CheckBoxListviewAdapter(PickTeamActivity.this,squad1Players,squad2Players));
        }

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        userId=pref.getLong("id",0);

    }

    public void submitTeam(View view) {
        errorMessageTextView.setVisibility(View.INVISIBLE);
        final List<Players> selectedPlayers = CheckBoxListviewAdapter.selectedPlayers;
        if(selectedPlayers.size()!=11){
            errorMessageTextView.setVisibility(View.VISIBLE);
            errorMessageTextView.setText(selectedPlayers.size()+ " selected, Select 11 players to continue ..");
            return;
        }

        matchId=selectedPlayers.get(0).getMatchId();

        CharSequence[] selectedPlayersString=new CharSequence[selectedPlayers.size()];
        int i=0;
        for (Players players: selectedPlayers){
            selectedPlayersString[i]=players.getName();
            i++;
        }

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Select captain and submit");
        dialogBuilder.setItems(selectedPlayersString, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Players captain = selectedPlayers.get(item);
                captain.setCaptain(true);
                final JSONArray jsonArray=new JSONArray();

                JSONObject finalJsonObject=new JSONObject();
                String url = "http://ec2-52-37-151-238.us-west-2.compute.amazonaws.com/v1/team_select/";
                    try {
                        for (Players players: selectedPlayers) {
                            JSONObject jsonObject=new JSONObject();
                            jsonObject.put("userId", userId);
                            jsonObject.put("matchId", players.getMatchId());
                            jsonObject.put("playerId", players.getId());
                            jsonObject.put("captain", players.isCaptain());
                            jsonArray.put(jsonObject);
                        }
                        finalJsonObject.put("matchPoints",jsonArray);
                        MyAsyncTask asyncTask =new MyAsyncTask(new AsyncResponse() {

                            @Override
                            public void processFinish(Object output) {
                                if(output==null){
                                    errorMessageTextView.setText("No internet connection");
                                    errorMessageTextView.setVisibility(View.VISIBLE);
                                    return;
                                }
                                Log.d(output.toString(),"Response From Asynchronous task:");
                                Intent intent = new Intent(PickTeamActivity.this, HomeActivity.class);
                                try {
                                    JSONObject jsonObject = (JSONObject) output;
                                    if(jsonObject.toString().contains("200")) {
                                        intent.putExtra("matchId",matchId);
                                        startActivity(intent);
                                    }
                                }
                                catch (Exception ex){
                                    errorMessageTextView.setText("Team submit Failed");
                                    errorMessageTextView.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                        asyncTask.execute(new Object[] { url ,"POST",finalJsonObject});
                    }

                    catch (Exception ex){

                    }
            }
        });
        //Create alert dialog object via builder
        AlertDialog alertDialogObject = dialogBuilder.create();
        //Show the dialog
        alertDialogObject.show();
    }
}
