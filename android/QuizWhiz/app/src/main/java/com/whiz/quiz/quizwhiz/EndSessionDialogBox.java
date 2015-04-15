package com.whiz.quiz.quizwhiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.whiz.quiz.quizwhiz.activity.Home;

/**
 * Created by John on 4/6/2015.
 */
public class EndSessionDialogBox extends DialogFragment {
    Activity activity = null;
    double grade = 0;

    TextView viewGrade = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.end_session_dialog, null);
        builder.setView(view);

        viewGrade = (TextView) view.findViewById(R.id.textGrade);

        viewGrade.setText("Grade: " + grade + "%");

        activity = getActivity();

        builder.setNeutralButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(activity, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                activity.startActivity(intent);

            }
        });


        return builder.create();
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
