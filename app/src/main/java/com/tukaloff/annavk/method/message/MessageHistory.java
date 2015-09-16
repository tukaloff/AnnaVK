package com.tukaloff.annavk.method.message;

import org.w3c.dom.Node;

import java.util.ArrayList;

/**
 * Created by user on 14.05.2015.
 */
public class MessageHistory {

    private int offset;
    private int user_id;
    private int count;
    private ArrayList<Message> msgHistory;

    public MessageHistory (int id, int offset) {
        this.user_id = id;
        this.count = 20;
        this.offset = offset;
        msgHistory = new ArrayList<>();
        MessagesGetHistory getHistory = new MessagesGetHistory(user_id, count, offset);
        getHistory.execute();
        ArrayList<Node> nodes =  getHistory.getNodes();
        for(int i = 0; i < nodes.size(); i++) {
            Message msg = new Message(nodes.get(i));
            msgHistory.add(msg);
        }
    }

    public MessageHistory (int id) {
        this.user_id = id;
        this.count = count;
    }

    public MessageHistory (int id, int count, int offset) {
        this.user_id = id;
        this.count = count;
        this.offset = offset;
    }

    public ArrayList<Message> get() {
        return msgHistory;
    }
}
