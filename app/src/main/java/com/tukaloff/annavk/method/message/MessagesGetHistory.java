package com.tukaloff.annavk.method.message;

import android.util.Log;

import com.tukaloff.annavk.networker.Connection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Created by user on 14.05.2015.
 */
public class MessagesGetHistory {
    private int user_id;
    private String answer;
    private Connection connection;
    private int count;
    private int offset;
    private Document XMLAnswer;

    private final String ANSWER_FORMAT_JSON = "";
    private final String ANSWER_FORMAT_XML = ".xml";
    private final String METHOD_NAME = "messages.getHistory";

    public MessagesGetHistory(int id, int count, int offset) {
        this.user_id = id;
        this.count = count;
        this.offset = offset;
    }

    public void execute() {
        ArrayList<ArrayList<String>> params = new ArrayList<>();
        if (count != 0) {
            ArrayList<String> alCount = new ArrayList<>();
            alCount.add("count");
            alCount.add(String.valueOf(count));
            params.add(alCount);
        }
        ArrayList<String> alOffset = new ArrayList<>();
        alOffset.add("offset");
        params.add(alOffset);
        alOffset.add(String.valueOf(offset));
        ArrayList<String> alUser_id = new ArrayList<>();
        alUser_id.add("user_id");
        alUser_id.add(String.valueOf(user_id));
        params.add(alUser_id );
        connection = new Connection(METHOD_NAME, ANSWER_FORMAT_XML, params, "get");
        connection.exec();
        answer = connection.getStringAnswer();
        Log.d("LogTag", "after: " + answer);
        XMLAnswer = connection.getXMLAnswer();
    }

    public String getAnswer() {
        return answer;
    }

    public ArrayList<Node> getNodes() {
        Element docElement = XMLAnswer.getDocumentElement();
        NodeList nodeList = docElement.getChildNodes();
        ArrayList<Node> arrList = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (!(nodeList.item(i).getNodeName().equals("#text"))&&
                    !(nodeList.item(i).getNodeName().equals("count"))) {
                arrList.add(nodeList.item(i));
            }
        }
        return arrList;
    }

}
