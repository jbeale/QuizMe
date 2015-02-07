package com.whiz.quiz.quizwhiz;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Registration extends ActionBarActivity {
    EditText editFirstName = null;
    EditText editLastName = null;
    EditText editEmail = null;
    EditText editPassword = null;
    Button buttonRegister = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editFirstName = (EditText) findViewById(R.id.editFirstName);
        editLastName = (EditText) findViewById(R.id.editLastName);
        editEmail = (EditText) findViewById(R.id.editEmailReg);
        editPassword = (EditText) findViewById(R.id.editPasswordReg);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO check to see if account already registered
                Boolean registered = false;
                if(registered){
                    String firstName = editFirstName.getText().toString();
                    String lastName = editLastName.getText().toString();
                    String email = editEmail.getText().toString();
                    String password = editPassword.getText().toString();

                    //TODO give information to server
                    //giveToServer(firstName, lastName, email, password)


                }
                else
                    ;//TODO Toast

            }
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
