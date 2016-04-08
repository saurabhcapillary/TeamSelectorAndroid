package saurabhkmr.teamselector.app;;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import saurabhkmr.teamselector.app.requests.AsyncResponse;
import saurabhkmr.teamselector.app.requests.MyAsyncTask;

import java.util.regex.Pattern;


public class LoginActivity extends Activity {

    EditText usernameText;
    EditText passwordText;
    TextView signupLink;
    Button loginButton;
    TextView errorTextView;
    public final static String EXTRA_MESSAGE = "com.example.super_selector_android.MESSAGE";
    private static final String TAG = LoginActivity.class.getSimpleName();
    protected static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signupLink=(TextView)findViewById(R.id.link_signup);
        loginButton=(Button)findViewById(R.id.btn_login);
        usernameText=(EditText)findViewById(R.id.input_username);
        passwordText=(EditText)findViewById(R.id.input_password);
        errorTextView=(TextView)findViewById(R.id.error_message);
        try {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            String name=pref.getString("name",null);
            if(name!=null){
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            }

        }
        catch (Exception ex){

        }
    }

    /** Called when the user clicks the register button */
    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        String message = usernameText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    /** Called when the user clicks the login button */
    public void login(View view) throws JSONException {
        errorTextView.setVisibility(View.INVISIBLE);
        errorTextView.setText("");
        String url = "http://ec2-52-11-41-143.us-west-2.compute.amazonaws.com/v1/user/login";
        JSONObject jsonObject=new JSONObject();
        String nickName=usernameText.getText().toString();
        String password=passwordText.getText().toString();
        jsonObject.put("nickName",nickName);
        jsonObject.put("passwordHash",password);
        MyAsyncTask asyncTask =new MyAsyncTask(new AsyncResponse() {

            @Override
            public void processFinish(Object output) {
                if(output==null){
                    errorTextView.setText("No internet connection");
                    errorTextView.setVisibility(View.VISIBLE);
                    return;
                }
                Log.d(output.toString(),"Response From Asynchronous task:");
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();
                try {
                    JSONObject jsonObject = (JSONObject) output;
                    JSONObject users = jsonObject.getJSONObject("users");
                    editor.putString("name",users.getString("name"));
                    editor.putString("email",users.getString("email"));
                    editor.putString("mobile",users.getString("mobile"));
                    editor.putString("nickName",users.getString("nickName"));
                    editor.putLong("points",users.getLong("totalPoints"));
                    editor.putLong("id",users.getLong("id"));
                    editor.commit();
                    startActivity(intent);
                }
                catch (Exception ex){
                    errorTextView.setText("Login Failed");
                    errorTextView.setVisibility(View.VISIBLE);
                }
            }
        });
        asyncTask.execute(new Object[] { url ,"POST",jsonObject});
    }
}
