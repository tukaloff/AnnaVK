package com.tukaloff.annavk.method.friends;

import com.tukaloff.annavk.networker.Connection;

import org.w3c.dom.Document;

import java.util.ArrayList;

/**
 * Created by user on 14.05.2015.
 */
public class FriendsGet {

    private final String ANSWER_FORMAT_JSON = "";
    private final String ANSWER_FORMAT_XML = ".xml";
    private final String METHOD_NAME = "friends.get";
    private final String ORDER_HINTS = "hints";

    private String answer;
    private Connection connection;
    private int id;
    private int count;

    public FriendsGet() {
        id = 76141154;
        count = 20;
    }
    public FriendsGet(int count) {
        id = 5282669;
        id = 76141154;
        this.count = count;
    }
    public void execute () {
        ArrayList<String> alUser_id = new ArrayList<>();
        alUser_id.add("user_id");
        alUser_id.add(String.valueOf(id));
        ArrayList<String> alOrder = new ArrayList<>();
        alOrder.add("order");
        alOrder.add(ORDER_HINTS);
        ArrayList<String> alCount = new ArrayList<>();
        alCount.add("count");
        alCount.add(String.valueOf(count));
        ArrayList<String> alFields = new ArrayList<>();
        alFields.add("fields");
        alFields.add("online");
        alFields.add("last_seen");
        alFields.add("photo_200");
        alFields.add("photo_200_orig");
        ArrayList<ArrayList<String>> params = new ArrayList<>();
        params.add(alUser_id);
        params.add(alOrder);
        params.add(alCount);
        params.add(alFields);
        connection = new Connection(METHOD_NAME, ANSWER_FORMAT_XML, params, "get");
        connection.exec();
        answer = connection.getStringAnswer();
    }

    public String getAnswer() {
        return answer;
    }

    public Document getXMLDoc() {
        return connection.getXMLAnswer();
    }
}
