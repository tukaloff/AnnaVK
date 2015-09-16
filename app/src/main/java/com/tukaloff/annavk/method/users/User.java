package com.tukaloff.annavk.method.users;

import android.text.style.TtsSpan;
import android.util.Log;


import com.tukaloff.annavk.utils.UnixTimeParcer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by user on 08.05.2015.
 */
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private int online;
    private String userString;
    private UsersGet usersGet;
    private Document userDoc;
    private long lastSeenTime;
    private int sex;
    private String nickname, domain, screen_name;
    private String bdate;
    private int city, country, timezone;
    private String photo_50, photo_100, photo_200, photo_max;
    private String photo_200_orig;

    public User(int id){
        usersGet = new UsersGet(id);
        usersGet.execute();
        userString = usersGet.getAnswer();
        getXMLUser();
    }

    public User(NodeList nodeList, int i) {
        runNodeList(nodeList, i);
    }

    public String getUserString() {
        return userString;
    }

    public Document getXMLUser() {
        userDoc = usersGet.getXMLDoc();
        extractFromDocument();
        return userDoc;
    }

    private void extractFromDocument() {
        Element docElement = userDoc.getDocumentElement();
        NodeList nodeList = docElement.getChildNodes();
        for(int i = 0; i < nodeList.getLength(); i++) {
            runNodeList(nodeList, i);
        }
    }

    private void runNodeList(NodeList nodeList, int i) {
        Log.d("LogTag", String.valueOf(i));
        Node node = nodeList.item(i);
        Log.d("LogTag", node.getNodeName() + node.getTextContent());
        switch (node.getNodeName()) {
            case "user":
                NodeList userNodeList = node.getChildNodes();
                for (int k = 0; k < userNodeList.getLength(); k++) {
                    Node userNode = userNodeList.item(k);
                    switch (userNode.getNodeName()) {
                        case "user_id":
                            id = Double.valueOf(userNode.getTextContent()).intValue();
                            break;
                        case "uid":
                            id = Double.valueOf(userNode.getTextContent()).intValue();
                            break;
                        case "first_name":
                            firstName = userNode.getTextContent();
                            break;
                        case "last_name":
                            lastName = userNode.getTextContent();
                            break;
                        case "online":
                            if (userNode.getTextContent().equals("0")) {
                                online = 0;
                            } else {
                                NodeList onlineNodeList = userNode.getChildNodes();
                                for (int m = 0; m < onlineNodeList.getLength(); m++) {
                                    Node onlineNode = onlineNodeList.item(m);
                                    switch (onlineNode.getNodeName()) {
                                        case "":
                                            break;
                                    }
                                }
                                online = 1;
                            }
                            break;
                        case "last_seen":
                            NodeList lastSeenList = userNode.getChildNodes();
                            for (int m = 0; m < lastSeenList.getLength(); m++) {
                                Node lastSeenNode = lastSeenList.item(m);
                                switch (lastSeenNode.getNodeName()) {
                                    case "time":
                                        lastSeenTime = Double.valueOf(lastSeenNode.getTextContent()).longValue();
                                        break;
                                }
                            }
                            break;
                        case "sex":
                            sex = Integer.parseInt(userNode.getTextContent());
                            break;
                        case "nickname":
                            nickname = userNode.getTextContent();
                            break;
                        case "domain":
                            domain = userNode.getTextContent();
                            break;
                        case "screen_name":
                            screen_name = userNode.getTextContent();
                            break;
                        case "bdate":
                            bdate = userNode.getTextContent();
                            break;
                        case "city":
                            city = Integer.parseInt(userNode.getTextContent());
                            break;
                        case "country":
                            country = Integer.parseInt(userNode.getTextContent());
                            break;
                        case "timezone":
                            timezone = Integer.parseInt(userNode.getTextContent());
                            break;
                        case "photo_50":
                            photo_50 =  userNode.getTextContent();
                            break;
                        case "photo_100":
                            photo_100 = userNode.getTextContent();
                            break;
                        case "photo_200":
                            photo_200 = userNode.getTextContent();
                            break;
                        case "photo_max":
                            photo_max = userNode.getTextContent();
                            break;
                        case "photo_200_orig":
                            photo_200_orig = userNode.getTextContent();
                            break;
                    }
                }
                break;
        }
    }

    public String getFirstName() {
        try {
            Log.d("LogTag", firstName);
            return firstName;
        } catch (Exception e) {
            Log.d("LogTag", e.getMessage());
            return e.getMessage();
        }
    }

    public String getLastName() {
        return lastName;
    }

    public int getOnline() {
        return online;
    }

    public long getLastSeen() {
        return lastSeenTime;
    }

    public String toStringLastSeen() {
        UnixTimeParcer ut = new UnixTimeParcer(lastSeenTime);
        return ut.getSDateTTime();
    }

    public int getUser_id() {
        return id;
    }

    public String getMainPhotoURI() {
        return photo_200;
    }

    public String getMainMaxPhotoURI() {
        return photo_200_orig;
    }
}
