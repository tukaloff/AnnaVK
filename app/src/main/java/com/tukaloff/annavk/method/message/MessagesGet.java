package com.tukaloff.annavk.method.message;

import android.util.Log;

import java.util.ArrayList;
import com.tukaloff.annavk.networker.Connection;

import org.w3c.dom.Document;

/**
 * Created by user on 14.05.2015.
 */
public class MessagesGet {
    private final String ANSWER_FORMAT_JSON = "";
    private final String ANSWER_FORMAT_XML = ".xml";
    private final String METHOD_NAME = "messages.get";
    private final String ORDER_HINTS = "hints";

    Connection connection;
    private String answer;

    public MessagesGet () {

    }

    public void execute () {
        ArrayList<String> alOut = new ArrayList<>();
        alOut.add("out");
        alOut.add("0");
        ArrayList<ArrayList<String>> params = new ArrayList<>();
        params.add(alOut);
        connection = new Connection(METHOD_NAME, ANSWER_FORMAT_XML, params, "get");
        connection.exec();
        answer = connection.getStringAnswer();
        Log.d("LogTag", "Exec");
    }

    public String getAnswer() {
        return answer;
    }

    public Document getXMLDoc() {
        return connection.getXMLAnswer();
    }

}
