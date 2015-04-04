package com.whiz.quiz.quizwhiz;

import android.app.Activity;
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
import com.whiz.quiz.quizwhiz.model.server_model.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;


/**
 * Created by Rabia on 2/23/2015.
 */


public class SessionKeyDialogBox extends DialogFragment{

    EditText editSessionKey = null;
    Activity activity = null;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://take.justinbeale.com");
        } catch (URISyntaxException e) {}
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.session_key_dialog, null);
        builder.setView(view);

        activity = getActivity();

        mSocket.on("join result", onJoinResult);
        mSocket.connect();
        QuizWhiz.mSocket = mSocket;

        editSessionKey = (EditText) view.findViewById(R.id.editSessionKey);
        editSessionKey.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String sessionKey = editSessionKey.getText().toString();
                attemptSend(sessionKey);
                /*
                if(isKeyAuthenticated(sessionKey)){ //TODO change fake authenticator
                    Intent intent = new Intent(getActivity().getApplicationContext(), WaitQuiz.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    intent.putExtra("sessionKey", sessionKey);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getActivity().getApplicationContext(), "Key could not be found", Toast.LENGTH_SHORT).show();
                    */

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



    private void attemptSend(String sessionKey) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String authToken = sharedPreferences.getString("authToken", "");

        mSocket.emit("join session", authToken, sessionKey);
        Toast.makeText(getActivity().getApplicationContext(), "Key sent", Toast.LENGTH_SHORT).show();
    }

    private Emitter.Listener onJoinResult = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Boolean success = (Boolean) args[0];
            if(success){
                JSONObject JSONsession = (JSONObject) args[1];
                String sessionName = null;
                int sessionCode = -1;
                int numQuestions = -1;
                Boolean isHost = false;

                try {
                    sessionName =  JSONsession.getString("sessionName");
                    sessionCode = JSONsession.getInt("sessionCode");
                    numQuestions = JSONsession.getInt("numQuestions");
                    isHost = JSONsession.getBoolean("isHost");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(activity, WaitQuiz.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra("numQuestions", numQuestions);
                intent.putExtra("isHost", isHost);
                startActivity(intent);
            }
            else{
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity.getApplicationContext(), "Key could not be found", Toast.LENGTH_SHORT).show();
                    }
                });
            }

    }
    };
}


