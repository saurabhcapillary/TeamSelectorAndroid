package saurabhkmr.teamselector.app;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;
import saurabhkmr.teamselector.app.Utils.Utils;
import saurabhkmr.teamselector.app.adapter.UserPointsAdapter;
import saurabhkmr.teamselector.app.models.User;
import saurabhkmr.teamselector.app.requests.AsyncResponse;
import saurabhkmr.teamselector.app.requests.MyAsyncTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saurabhkmr on 10/4/16 @Capillary Tech
 */
public class CurrentMatchLeaderBoard extends BaseActivity {
    ListView lv;
    Context context;
    List<User> users;
    TextView leaderViewHeader;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leader_board);
        context = this;
        lv = (ListView) findViewById(R.id.leaderboardListView);
        leaderViewHeader=(TextView) findViewById(R.id.leaderboard_header);
        getUsersPoints();
    }

    public void getUsersPoints() {
        users = new ArrayList<>();
        long matchId = Utils.getCurrentMatchId();
        if (matchId == 0) {
            leaderViewHeader.setText("Match not started");
        } else {
            String urlKkr = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/user/points?match_id="+matchId;
            MyAsyncTask asyncTaskKkr = new MyAsyncTask(new AsyncResponse() {
                @Override
                public void processFinish(Object output) {
                    if (output == null) {
                        return;
                    }
                    Log.d(output.toString(), "Response From Asynchronous task:");
                    try {
                        JSONObject jsonObject = (JSONObject) output;
                        JSONArray userPoints = (JSONArray) jsonObject.get("user_points");
                        for (int i = 0; i < userPoints.length(); i++) {
                            User user = new User();
                            user.setId(userPoints.getJSONObject(i).getLong("userId"));
                            user.setPoints(userPoints.getJSONObject(i).getLong("points"));
                            user.setName(userPoints.getJSONObject(i).getString("name"));
                            user.setRank(userPoints.getJSONObject(i).getInt("rank"));
                            users.add(user);
                        }
                        lv.setAdapter(new UserPointsAdapter(CurrentMatchLeaderBoard.this, null, users));

                        Log.d(userPoints.getString(0), "players");
                    } catch (Exception ex) {
                        Log.d("squads", ex.getMessage());
                    }
                }
            });
            asyncTaskKkr.execute(new Object[]{urlKkr, "GET"});
        }
    }
}
