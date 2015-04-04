package com.whiz.quiz.quizwhiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.whiz.quiz.quizwhiz.activity.WaitQuiz;

import java.net.URISyntaxException;


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

        mSocket.on("join result", onJoinResult);

        mSocket.connect();

        editSessionKey = (EditText) view.findViewById(R.id.editSessionKey);
        editSessionKey.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String sessionKey = editSessionKey.getText().toString();
                //TODO USE attemptSend(sessionKey);
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

        return builder.create();
    }

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("quizwhiz.justinbeale.com:3001");
        } catch (URISyntaxException e) {}
    }

    private void attemptSend(String sessionKey) { //TODO in progress
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String authToken = sharedPreferences.getString("authToken", "");

        mSocket.emit("join session", authToken, sessionKey);
    }

    private Emitter.Listener onJoinResult = new Emitter.Listener() {
        @Override
        public void call(Object... args) { //TODO ASK "What is a session object?"
            Boolean success = (Boolean) args[0];
            int numQuestions = 0; //TODO somehow get this
            if(success){
                Intent intent = new Intent(getActivity().getApplicationContext(), WaitQuiz.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra("numQuestions", numQuestions);
                startActivity(intent);
            }
            else
                ;
        }
    };
}


