package saurabhkmr.teamselector.app.requests;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by saurabhkmr on 13/3/16.
 */
public class RestService {

   /* public static JSONObject doGet(Object url) {
        JSONObject json = null;

        HttpClient httpclient = new DefaultHttpClient();
        // Prepare a request object
        HttpGet httpget = new HttpGet(url.toString());
        // Accept JSON
        httpget.addHeader("accept", "application/json");

        httpget.setHeader("User-Agent", "shubhra");

        // Execute the request
        HttpResponse response;

        try {
            response = httpclient.execute(httpget);
            // Get the response entity
            // Log.e("myApp", "Issue is here...!");
            HttpEntity entity = response.getEntity();
            // If response entity is not null
            if (entity != null) {
                // get entity contents and convert it to string
                InputStream instream = entity.getContent();
                String result= convertStreamToString(instream);
                // construct a JSON object with result
                json=new JSONObject(result);
                // Closing the input stream will trigger connection release
                instream.close();
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Return the json
        return json;
    }*/

    public static JSONObject sendGET(Object url){
        JSONObject json = null;
        try {
            URL obj = new URL(url.toString());
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "shubhra");
            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result
                System.out.println(response.toString());
                json = new JSONObject(response.toString());
            } else {
                System.out.println("GET request not worked");
            }
        }
        catch (Exception ex){
        }
        return json;
    }


    private static String convertStreamToString(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }


   /* public static JSONObject sendPost(Object url,Object param)  {
        HttpClient httpClient = new DefaultHttpClient();
        JSONObject json = null;
        try {
            HttpPost request = new HttpPost(url.toString());
            StringEntity params =new StringEntity(param.toString());
            request.addHeader("content-type", "application/json");
            request.addHeader("Accept","application/json");
            request.setHeader("User-Agent", "shubhra");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            String responseString = new BasicResponseHandler().handleResponse(response);
            json=new JSONObject(responseString);
            // handle response here...
        }catch (Exception ex) {
            // handle exception here
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return  json;
    }*/

    public static JSONObject sendPOST(Object url,JSONObject param)  {
        JSONObject jsonObject=null;
        HttpURLConnection connection = null;
        try {
            URL urlConn=new URL(url.toString());
            StringEntity params =new StringEntity(param.toString());
            connection = (HttpURLConnection) urlConn.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "shubhra");
       //     OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
         //   streamWriter.write(params.toString());
           // streamWriter.flush();
            OutputStream out = connection.getOutputStream();
            out.write(param.toString().getBytes());
            out.flush();
            out.close();
            int responseCode = connection.getResponseCode();
            StringBuilder stringBuilder = new StringBuilder();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String response = null;
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response + "\n");
                }
                bufferedReader.close();

                Log.d("test", stringBuilder.toString());
                jsonObject=new JSONObject(stringBuilder.toString());
            } else {
                Log.e("test", connection.getResponseMessage());
                return null;
            }
        } catch (Exception exception){
            Log.e("test", exception.toString());
            return null;
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
        return jsonObject;
    }
}
