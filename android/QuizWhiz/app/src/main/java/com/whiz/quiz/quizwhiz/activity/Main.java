package com.whiz.quiz.quizwhiz.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.whiz.quiz.quizwhiz.R;
import com.whiz.quiz.quizwhiz.model.response.LoginResponseBody;
import com.whiz.quiz.quizwhiz.model.response.RestResponse;
import com.whiz.quiz.quizwhiz.service.RestClient;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Main extends Activity {
    EditText editUsername = null;
    EditText editPassword = null;
    Button buttonLogin = null;
    Button buttonSignUp = null;
    SharedPreferences sharedPreferences = null;
    TextView textWebsite = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);



        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPasswordReg);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);
        textWebsite = (TextView) findViewById(R.id.textWebsite);

        textWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://quizwhiz.co"));
                startActivity(intent);
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Registration.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(intent);
            }

        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameText = editUsername.getText().toString();
                String passwordText = editPassword.getText().toString();

                login(usernameText, passwordText);

                /*Intent intent = new Intent(v.getContext(), LoginService.class);
                intent.putExtra("com.whiz.quiz.quizwhiz.USERNAME", usernameText); //Hopefully Identification is fine
                intent.putExtra("com.whiz.quiz.quizwhiz.PASSWORD", passwordText);
                startService(intent);*/
            }
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public void login(String username, String password) {
        RestClient restClient = new RestClient(getApplicationContext());
        restClient.get().login(username, password, new Callback<RestResponse<LoginResponseBody>>() {

            @Override
            public void success(RestResponse<LoginResponseBody> loginResponseBodyRestResponse, Response response) {
                savePreferences("authToken", loginResponseBodyRestResponse.body.authToken);

                Intent intent = new Intent(getApplicationContext(), Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), error.getResponse().getReason().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    };

    private void savePreferences(String key, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
