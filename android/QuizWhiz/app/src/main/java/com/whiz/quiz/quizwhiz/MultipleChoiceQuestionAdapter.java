package com.whiz.quiz.quizwhiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JohnMain on 2/21/2015.
 */
public class MultipleChoiceQuestionAdapter extends BaseAdapter {
    Context context = null;
    ArrayList<MultipleChoiceQuestion> multipleChoiceQuestions = null;
    Activity activity;

    public MultipleChoiceQuestionAdapter(Context _context, ArrayList<MultipleChoiceQuestion> _multipleChoiceQuestions, Activity _activity){
        context = _context;
        multipleChoiceQuestions = _multipleChoiceQuestions;
        activity = _activity;
    }

    @Override
    public int getCount() {
        return multipleChoiceQuestions.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.multiple_choice_list, null);
        }

        TextView questionName = (TextView) convertView.findViewById(R.id.textQuestionName);
        TextView question = (TextView) convertView.findViewById(R.id.textQuestion);
        TextView[] options = new TextView[4];
        options[0] = (TextView) convertView.findViewById(R.id.textAnswer1);
        options[1] = (TextView) convertView.findViewById(R.id.textAnswer2);
        options[2] = (TextView) convertView.findViewById(R.id.textAnswer3);
        options[3] = (TextView) convertView.findViewById(R.id.textAnswer4);
        RadioGroup radioGroup = (RadioGroup) convertView.findViewById(R.id.radioGroup);
        RadioButton radioButton1 = (RadioButton) convertView.findViewById(R.id.radioButton5);
        RadioButton radioButton2 = (RadioButton) convertView.findViewById(R.id.radioButton6);
        RadioButton radioButton3 = (RadioButton) convertView.findViewById(R.id.radioButton7);
        RadioButton radioButton4 = (RadioButton) convertView.findViewById(R.id.radioButton8);

        MultipleChoiceQuestion multipleChoiceQuestion = multipleChoiceQuestions.get(position);
        questionName.setText(multipleChoiceQuestion.getQuestionName());
        question.setText(multipleChoiceQuestion.getQuestion());
        for(int i=0; i<4; i++){
            options[i].setText(multipleChoiceQuestion.getPossibleAnswers()[i]);
        }

        switch (multipleChoiceQuestion.getCorrectAnswerPosition()){
            case 0:
                radioGroup.check(radioButton1.getId());
                break;
            case 1:
                radioGroup.check(radioButton2.getId());
                break;
            case 2:
                radioGroup.check(radioButton3.getId());
                break;
            case 3:
                radioGroup.check(radioButton4.getId());
                break;
        }
        radioGroup.setEnabled(false);
        radioButton1.setEnabled(false);
        radioButton2.setEnabled(false);
        radioButton3.setEnabled(false);
        radioButton4.setEnabled(false);


/*
     //TODO On long click, open up a dialog box for edition or deletion
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Edit Question " + questionNumber + "?");
                String[] boxValues = {"Delete"};
                builder.setItems(boxValues, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                //TODO Delete
                                break;
                        }

                    }
                });
                builder.show();

                return false;
            }
        });


        RadioGroup radioGroup = (RadioGroup) convertView.findViewById(R.id.radioGroup);
        RadioButton btnCorrectAnswer = (RadioButton) convertView.findViewById(radioGroup.getCheckedRadioButtonId());

        String[] options = new String[4];
        options[0] = convertView.findViewById(R.id.editAnswer1).toString();
        options[1] = convertView.findViewById(R.id.editAnswer2).toString();
        options[2] = convertView.findViewById(R.id.editAnswer3).toString();
        options[3] = convertView.findViewById(R.id.editAnswer4).toString();

        MultipleChoiceQuestion multipleChoiceQuestion = multipleChoiceQuestions.get(position);

        multipleChoiceQuestion.setQuestion(question.toString());
        multipleChoiceQuestion.setPossibleAnswers(options);
        multipleChoiceQuestion.setCorrectAnswerPosition((Integer) btnCorrectAnswer.getTag());
*/
        return convertView;
    }
}
