package com.tukaloff.annavk.method.message;

import android.util.Log;

import com.tukaloff.annavk.utils.UnixTimeParcer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by user on 14.05.2015.
 */
public class Message {

    private Node node;
    private String body, mid, uid, from_id, date, read_state, out;
    ArrayList<Object> parcedAttachments = new ArrayList<>();

    public Message(Node node) {
        this.node = node;
        parseNode();
    }

    public static void send(int id, String msg) {
        Log.d("LogTag", "in");
        MessagesSend objSend = new MessagesSend(id, msg);
        objSend.execute();
    }

    private void parseNode() {
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nodeElement = nodeList.item(i);
            switch (nodeElement.getNodeName()) {
                case "body":
                    body = nodeElement.getTextContent();
                    break;
                case "mid":
                    mid = nodeElement.getTextContent();
                    break;
                case "uid":
                    uid = nodeElement.getTextContent();
                    break;
                case "from_id":
                    from_id = nodeElement.getTextContent();
                    break;
                case "date":
                    date = nodeElement.getTextContent();
                    break;
                case "read_state":
                    read_state = nodeElement.getTextContent();
                    break;
                case "out":
                    out = nodeElement.getTextContent();
                    break;
                case "attachment":
                    parceAttachments(nodeElement.getChildNodes());
                    break;
                case "fwd_messages":
                    parceFwd_messages(nodeElement);
                    break;
                default:
                    break;
            }
        }
    }

    private void parceFwd_messages(Node childNodes) {
        if (!childNodes.getNodeName().equals("#text")) {
            parcedAttachments.add(childNodes);
        }
    }

    private void parceAttachments(NodeList nodeList) {
        ArrayList<Object> arrList = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (!nodeList.item(i).getNodeName().equals("#text")) {
                arrList.add(nodeList.item(i));
            }
        }

        Log.d("LogTag", "-1-1-1-1-1-1   " + ((Node) arrList.get(0)).getTextContent());

        switch (((Node)arrList.get(0)).getTextContent()) {
            case "wall":
                arrList.set(0, "wall");
                break;
            case "photo":
                arrList.set(0, "photo");
                break;
            case "doc":
                arrList.set(0, "doc");
                break;
            case "video":
                arrList.set(0, "video");
                break;
            case "sticker":
                arrList.set(0, "sticker");
                break;
            case "wall_reply":
                arrList.set(0, "wall_reply");
                break;
            case "audio":
                arrList.set(0, "audio");
                break;
            default:
                break;
        }

        switch ((String)arrList.get(0)) {
            case "wall":
                parcedAttachments.add(arrList.get(1));
                break;
            case "photo":
                parcedAttachments.add(arrList.get(1));
                break;
            case "doc":
                parcedAttachments.add(arrList.get(1));
                break;
            case "video":
                parcedAttachments.add(arrList.get(1));
                break;
            case "sticker":
                parcedAttachments.add(arrList.get(1));
                break;
            case "wall_reply":
                parcedAttachments.add(arrList.get(1));
                break;
            case "audio":
                parcedAttachments.add(arrList.get(1));
                break;
            default:
                break;
        }
    }

    public boolean haveAttachments() {
        if (parcedAttachments.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public String getBody() {
        return body;
    }

    public int getMid() {
        return Double.valueOf(mid).intValue();
    }

    public int getUid() {
        return Double.valueOf(uid).intValue();
    }

    public int getFrom_id() {
        return Double.valueOf(from_id).intValue();
    }

    public String getDateTime() {
        UnixTimeParcer ut = new UnixTimeParcer(Double.valueOf(date).longValue());
        return ut.getSDateTTime();
    }

    public boolean readState() {
        if (read_state.equals("0")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isOut() {
        if (out.equals("0")) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Object> getAttach() {
        return parcedAttachments;
    }
}
