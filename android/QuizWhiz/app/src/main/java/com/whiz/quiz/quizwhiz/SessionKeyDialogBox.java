package com.whiz.quiz.quizwhiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.view.View;


/**
 * Created by Rabia on 2/23/2015.
 */


public class SessionKeyDialogBox extends DialogFragment{

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final EditText editSessionKey = null;



            LayoutInflater inflater = getActivity().getLayoutInflater();

            builder.setView(inflater.inflate(R.layout.session_key_dialog, null));
                   builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            editSessionKey = (EditText) findViewById(R.id.editSessionKey);
                            String sessionKey = editSessionKey.getText().toString();




                        }
                    });
                  builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int id) {
                                setCancelable(true);
                              }
                          }
                  );

        return builder.create();}}
