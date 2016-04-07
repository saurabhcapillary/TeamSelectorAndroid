package saurabhkmr.teamselector.app;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

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

    }
}
