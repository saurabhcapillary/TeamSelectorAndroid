package saurabhkmr.teamselector.app;;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import saurabhkmr.teamselector.app.requests.AsyncResponse;
import saurabhkmr.teamselector.app.requests.MyAsyncTask;

import java.util.regex.Pattern;

/**
 * Created by saurabhkmr on 6/3/16.
 */
public class RegisterActivity extends Activity {

    EditText nameText;
    EditText mobileText;
    EditText nickNameText;
    EditText cityText;
    EditText emailText;
    EditText passwordText;
    TextView errorTextView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        errorTextView=(TextView)findViewById(R.id.registerErrorMessage);
        emailText=(EditText)findViewById(R.id.registerEmail);
        passwordText=(EditText)findViewById(R.id.registerPassword);
        cityText=(EditText)findViewById(R.id.registerCity);
        mobileText=(EditText)findViewById(R.id.registerMobile);
        nameText=(EditText)findViewById(R.id.registerName);
        nickNameText=(EditText)findViewById(R.id.registerNickname);
    }

    public void register(View view) throws JSONException{
        errorTextView.setVisibility(View.INVISIBLE);
        errorTextView.setTextColor(getResources().getColor(R.color.red));
        errorTextView.setText("");
        String url = "http://ec2-52-37-151-238.us-west-2.compute.amazonaws.com/v1/user/";
        JSONObject jsonObject=new JSONObject();
        String email=emailText.getText().toString();
        String password=passwordText.getText().toString().trim();
        String name=nameText.getText().toString();
        String nickName=nickNameText.getText().toString().trim();
        String mobile=mobileText.getText().toString();
        String city=cityText.getText().toString();

        jsonObject.put("passwordHash",password);
        jsonObject.put("email",email);
        jsonObject.put("city",city);
        jsonObject.put("name",name);
        jsonObject.put("nickName",nickName);
        jsonObject.put("mobile",mobile);

        if(nickName==""){
            errorTextView.setText("Nickname is mandatory");
            errorTextView.setVisibility(View.VISIBLE);
            return;
        }

        if(name==""){
            errorTextView.setText("Name is mandatory");
            errorTextView.setVisibility(View.VISIBLE);
            return;
        }

        if(password==""){
            errorTextView.setText("Password is mandatory");
            errorTextView.setVisibility(View.VISIBLE);
            return;
        }

        MyAsyncTask asyncTask =new MyAsyncTask(new AsyncResponse() {

            @Override
            public void processFinish(Object output) {
                if(output==null){
                    errorTextView.setText("No internet connection");
                    errorTextView.setVisibility(View.VISIBLE);
                    return;
                }
                Log.d(output.toString(),"Response From Asynchronous task:");
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                try {
                    errorTextView.setTextColor(getResources().getColor(R.color.green));
                    errorTextView.setText("Registration successful");
                    startActivity(intent);
                }
                catch (Exception ex){
                    errorTextView.setText("Registration Failed");
                    errorTextView.setVisibility(View.VISIBLE);
                }
            }
        });
        asyncTask.execute(new Object[] { url ,"POST",jsonObject});
    }
}
