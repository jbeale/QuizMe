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

        EditText question = (EditText) convertView.findViewById(R.id.editQuestion);
        final int questionNumber = position + 1;
        question.setHint("Question " + questionNumber);
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
