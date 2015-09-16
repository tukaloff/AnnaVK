package com.tukaloff.annavk.method.message;

import android.util.Log;

import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Created by user on 23.05.2015.
 */
public class MessagesLast {

    public ArrayList<Message> MessagesLast () {
        MessagesGet mGet = new MessagesGet();
        mGet.execute();
        NodeList nodeList = mGet.getXMLDoc().getDocumentElement().getChildNodes();
        ArrayList<Message> msgArray = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (!(nodeList.item(i).getNodeName().equals("#text"))&&
                    !(nodeList.item(i).getNodeName().equals("count"))) {
                Message msg = new Message(nodeList.item(i));
                msgArray.add(msg);
            }
        }
        return msgArray;
    }

}
