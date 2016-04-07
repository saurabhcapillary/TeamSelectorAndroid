package saurabhkmr.teamselector.app.requests;

import android.os.AsyncTask;
import org.json.JSONObject;

/**
 * Created by saurabhkmr on 13/3/16.
 */
public class MyAsyncTask extends AsyncTask<Object,Object,JSONObject> {

    public AsyncResponse delegate = null;//Call back interface

    public MyAsyncTask(AsyncResponse asyncResponse) {
        delegate = asyncResponse;//Assigning call back interfacethrough constructor
    }

    @Override
    protected JSONObject doInBackground(Object... params) {

        //My Background tasks are written here
        if("GET".equals(params[1])) {
            return RestService.sendGET(params[0]);
        }
        else{
            return RestService.sendPOST(params[0],(JSONObject)params[2]);
        }
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        delegate.processFinish(result);
    }

  /*  @Override
    protected JSONObject doInBackground(String... urls) {
        return RestService.doGet(urls[0]);
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        Log.i("hello",jsonObject.toString());
    }*/

}
