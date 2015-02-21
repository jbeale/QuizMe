package com.whiz.quiz.quizwhiz;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.multiple_choice_list, null);
        }

        EditText question = (EditText) convertView.findViewById(R.id.editQuestion);
        EditText[] answers = new EditText[4];
        answers[0] = (EditText) convertView.findViewById(R.id.editAnswer1);
        answers[1] = (EditText) convertView.findViewById(R.id.editAnswer2);
        answers[2] = (EditText) convertView.findViewById(R.id.editAnswer3);
        answers[3] = (EditText) convertView.findViewById(R.id.editAnswer4);

        MultipleChoiceQuestion multipleChoiceQuestion = multipleChoiceQuestions.get(position);

        return convertView;
    }
}
