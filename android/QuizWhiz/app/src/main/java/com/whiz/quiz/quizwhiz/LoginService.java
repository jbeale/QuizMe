package com.whiz.quiz.quizwhiz;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by JohnMain on 2/6/2015.
 */

public class LoginService extends IntentService{

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    public LoginService(){
        super("LoginService");
    }

    public LoginService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String username = intent.getStringExtra("com.whiz.quiz.quizwhiz.USERNAME");
        String password = intent.getStringExtra("com.whiz.quiz.quizwhiz.PASSWORD");

        String url = "http://s-quizme.justinbeale.com/service/auth/login";

        URL obj1 = null;
        try {
            obj1 = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection con = (HttpURLConnection) obj1.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject obj = new JSONObject();

        try {
            obj.put("username", username);
            obj.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String jsonText = obj.toString();

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(jsonText);
        HttpResponse httpResponse;
        String jsonString = "";
        StringBuilder builder = new StringBuilder();

        try {
            httpResponse = httpClient.execute(httpPost);
            StatusLine statusLine = httpResponse.getStatusLine();
            if (statusLine.getStatusCode() == 200){
                HttpEntity entity = httpResponse.getEntity();
                InputStream stream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                while ((jsonString = reader.readLine()) != null){
                    builder.append(jsonString);
                }
                jsonString = builder.toString();
                Toast.makeText(this, jsonString.toString(), Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Broadcast the Results
        Intent i = new Intent("com.whiz.quiz.quizwhiz.AUTHENTICATED");
        i.putExtra("com.whiz.quiz.quizwhiz.USERNAME", username);
        i.putExtra("com.whiz.quiz.quizwhiz.PASSWORD", password);
        sendBroadcast(i); 
    }
}