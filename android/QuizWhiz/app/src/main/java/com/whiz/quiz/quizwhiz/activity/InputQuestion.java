package com.whiz.quiz.quizwhiz.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.whiz.quiz.quizwhiz.R;


public class InputQuestion extends ActionBarActivity {
    EditText editQuestionName = null;
    EditText editQuestion = null;
    EditText[] editOptions = new EditText[4];
    RadioGroup radioGroup = null;
    Button btnSave = null;
    Button btnCancel = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_question);

        editQuestionName = (EditText) findViewById(R.id.editQuestionName);
        editQuestion = (EditText) findViewById(R.id.textQuestion);
        editOptions[0] = (EditText) findViewById(R.id.textAnswer1);
        editOptions[1] = (EditText) findViewById(R.id.textAnswer2);
        editOptions[2] = (EditText) findViewById(R.id.textAnswer3);
        editOptions[3] = (EditText) findViewById(R.id.editAnswer4);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup2);
        btnSave = (Button) findViewById(R.id.buttonSave);
        btnCancel = (Button) findViewById(R.id.buttonCancel);



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("questionName", editQuestionName.getText().toString());
                bundle.putString("question", editQuestion.getText().toString());
                String[] options = new String[4];
                for (int i=0; i<4; i++){
                    bundle.putString("option" + i, editOptions[i].getText().toString());
                }
                RadioButton radioButtonChecked =
                        (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                int index;
                if(radioButtonChecked != null) {
                    index = Integer.parseInt((String) radioButtonChecked.getTag());
                }
                else
                    index = 0;

                bundle.putInt("index", index);

                Intent intent = new Intent();
                intent.putExtra("multipleChoiceBundle", bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_input_question, menu);
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
