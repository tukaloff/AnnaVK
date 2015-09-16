package com.tukaloff.annavk.method.message;

import android.util.Log;

import com.tukaloff.annavk.networker.Connection;

import java.util.ArrayList;

/**
 * Created by user on 16.05.2015.
 */
public class MessagesSend {
    private final String ANSWER_FORMAT_JSON = "";
    private final String ANSWER_FORMAT_XML = ".xml";
    private final String METHOD_NAME = "messages.send";

    Connection connection;
    private int user_id;
    private String msg;
    private String answer;

    public MessagesSend(int id, String msg) {
        this.user_id = id;
        this.msg = msg;
    }

    public void execute() {
        ArrayList<ArrayList<String>> params = new ArrayList<>();
        ArrayList<String> alUser = new ArrayList<>();
        alUser.add("user_id");
        alUser.add(String.valueOf(user_id));
        ArrayList<String> alMsg = new ArrayList<>();
        alMsg.add("message");
        alMsg.add(msg);
        //"v=5.28&"
        ArrayList<String> alV = new ArrayList<>();
        alV.add("v");
        alV.add("5.28");
        params.add(alUser);
        params.add(alMsg);
        params.add(alV);
        Log.d("LogTag", "in");
        connection = new Connection(METHOD_NAME, ANSWER_FORMAT_XML, params, "post");
        connection.exec();
        Log.d("LogTag", "out");
        answer = connection.getStringAnswer();
    }
}
