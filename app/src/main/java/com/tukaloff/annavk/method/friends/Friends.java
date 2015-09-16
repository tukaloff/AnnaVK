package com.tukaloff.annavk.method.friends;

import android.util.Log;

import com.tukaloff.annavk.method.users.User;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Created by user on 14.05.2015.
 */
public class Friends/* extends User */{

    private ArrayList<User> friends;

    public Friends(int count) {
        friends = new ArrayList<>();
        FriendsGet friendsGet = new FriendsGet(count);
        friendsGet.execute();
        Document friendsDoc = friendsGet.getXMLDoc();
        Element docElement = friendsDoc.getDocumentElement();
        NodeList nodeList = docElement.getChildNodes();
        Log.d("LogTag", String.valueOf(nodeList.getLength()));
        for(int i = 0; i < nodeList.getLength(); i++) {
            if (!(nodeList.item(i).getNodeName().equals("#text"))) {
                User user = new User(nodeList, i);
                friends.add(user);
                Log.d("LogTag", user.getLastName());
            }
        }
        Log.d("LogTag", docElement.getNodeName());
    }

    public ArrayList<User> getFriends() {
        return friends;
    }
}
