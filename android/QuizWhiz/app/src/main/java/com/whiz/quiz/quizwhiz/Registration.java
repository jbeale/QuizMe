package com.whiz.quiz.quizwhiz;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.whiz.quiz.quizwhiz.model.response.LoginResponseBody;
import com.whiz.quiz.quizwhiz.model.response.RestResponse;
import com.whiz.quiz.quizwhiz.service.RestClient;

import retrofit.Callback;


public class Registration extends ActionBarActivity {
    EditText editUsername = null;
    EditText editFirstName = null;
    EditText editLastName = null;
    EditText editEmail = null;
    EditText editPassword = null;
    Button buttonRegister = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editUsername = (EditText) findViewById(R.id.editUsername);
        editFirstName = (EditText) findViewById(R.id.editFirstName);
        editLastName = (EditText) findViewById(R.id.editLastName);
        editEmail = (EditText) findViewById(R.id.editEmailReg);
        editPassword = (EditText) findViewById(R.id.editPasswordReg);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                String usernameText = editUsername.getText().toString();
                String firstNameText = editFirstName.getText().toString();
                String lastNameText = editLastName.getText().toString();
                String emailText = editEmail.getText().toString();
                String passwordText = editPassword.getText().toString();

                registration(usernameText, firstNameText, lastNameText, emailText, passwordText);

            }
        });
    }

    public void registration(String username, String firstname, String lastname, String email, String password){
        RestClient.set().register(username, firstname, lastname, email, password, new Callback<RestResponse<LoginResponseBody>>(){
            
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
        return true;
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
