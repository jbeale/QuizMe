package com.whiz.quiz.quizwhiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import com.whiz.quiz.quizwhiz.activity.WaitQuiz;


/**
 * Created by Rabia on 2/23/2015.
 */


public class SessionKeyDialogBox extends DialogFragment{

    EditText editSessionKey = null;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.session_key_dialog, null);
        builder.setView(view);

        editSessionKey = (EditText) view.findViewById(R.id.editSessionKey);
        editSessionKey.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String sessionKey = editSessionKey.getText().toString();
                //TODO authenticateSessionKey(sessionKey)
                if(isKeyAuthenticated(sessionKey)){ //TODO change fake authenticator
                    Intent intent = new Intent(getActivity().getApplicationContext(), WaitQuiz.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    intent.putExtra("sessionKey", sessionKey);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getActivity().getApplicationContext(), "Key could not be found", Toast.LENGTH_SHORT).show();

            }

            private boolean isKeyAuthenticated(String sessionKey) {
                if (sessionKey.equals("aa")){
                    return true;
                }
                else
                    return false;
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int id) {
                    setCancelable(true);
                  }
              }
        );

        return builder.create();}}
