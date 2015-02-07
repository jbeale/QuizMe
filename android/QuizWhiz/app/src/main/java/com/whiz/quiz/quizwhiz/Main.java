package com.whiz.quiz.quizwhiz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Main extends ActionBarActivity {
    EditText editUsername = null;
    EditText editPassword = null;
    Button buttonLogin = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPasswordReg);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameText = editUsername.getText().toString();
                String passwordText = editPassword.getText().toString();

                Intent intent = new Intent(v.getContext(), LoginService.class);
                intent.putExtra("com.whiz.quiz.quizwhiz.USERNAME", usernameText); //Hopefully Identification is fine
                intent.putExtra("com.whiz.quiz.quizwhiz.PASSWORD", passwordText);
                startService(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void goToHome(String username, String password) {
        Intent intent = new Intent(this, Home.class);
        intent.putExtra("com.whiz.quiz.quizwhiz.USERNAME", username);
        intent.putExtra("com.whiz.quiz.quizwhiz.PASSWORD", password);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
}
