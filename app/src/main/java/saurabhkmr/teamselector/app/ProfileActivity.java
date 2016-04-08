package saurabhkmr.teamselector.app;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;
import saurabhkmr.teamselector.app.models.Players;
import saurabhkmr.teamselector.app.requests.AsyncResponse;
import saurabhkmr.teamselector.app.requests.MyAsyncTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by saurabhkmr on 27/3/16.
 */
public class ProfileActivity extends BaseActivity {

    TextView nameTextView;
    TextView emailTextView;
    TextView mobileTextView;
    TextView pointsTextView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String name=pref.getString("name","Not Available");
        String nickName=pref.getString("nickName","Not Available");
        String mobile=pref.getString("mobile","Not Available");
        String email=pref.getString("email","Not Available");
        long userId=pref.getLong("id",0);

        Long points=pref.getLong("points",0);

        nameTextView=(TextView)findViewById(R.id.profileName);
        emailTextView=(TextView)findViewById(R.id.profileEmail);
        mobileTextView=(TextView)findViewById(R.id.profileMobile);
        pointsTextView=(TextView)findViewById(R.id.profilePoints);
        pointsTextView=(TextView)findViewById(R.id.profilePoints);

        nameTextView.setText(name);
        emailTextView.setText("NickName: "+nickName);
        mobileTextView.setText("Mobile: #"+mobile);
        pointsTextView.setText(points+" points");

        getPoints(userId);
    }

    private void getPoints(long userId) {
        String urlKkr = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/user/points/"+userId;
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
                    String points = jsonObject.getString("points");
                    pointsTextView.setText(points);

                }
                catch (Exception ex){
                    Log.d("squads",ex.getMessage());
                }
            }
        });
        asyncTaskKkr.execute(new Object[] { urlKkr,"GET"});
    }
}
