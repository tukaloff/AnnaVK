package com.tukaloff.annavk.method.users;

import android.util.Log;

import com.tukaloff.annavk.networker.Connection;

import org.w3c.dom.Document;

import java.util.ArrayList;

/**
 * Created by user on 08.05.2015.
 */
public class UsersGet {

    private int id;
    private String answer;
    private Connection connection;
    private final String ANSWER_FORMAT_JSON = "";
    private final String ANSWER_FORMAT_XML = ".xml";
    private final String METHOD_NAME = "users.get";

    public UsersGet(int id) {
        this.id = id;
    }

    public void execute() {
        ArrayList<String> alUser_id = new ArrayList<String>();
        alUser_id.add("user_id");
        alUser_id.add(String.valueOf(id));
        ArrayList<String> alFields = new ArrayList<String>();
        alFields.add("fields");
        alFields.add("photo_id");
        alFields.add("verified");
        alFields.add("blacklisted");
        alFields.add("sex");
        alFields.add("bdate");
        alFields.add("city");
        alFields.add("country");
        alFields.add("home_town");
        alFields.add("photo_50");
        alFields.add("photo_100");
        alFields.add("photo_200_orig");
        alFields.add("photo_200");
        alFields.add("photo_400_orig");
        alFields.add("photo_max");
        alFields.add("photo_max_orig");
        alFields.add("online");
        alFields.add("lists");
        alFields.add("domain");
        alFields.add("has_mobile");
        alFields.add("contacts");
        alFields.add("site");
        alFields.add("education");
        alFields.add("universities");
        alFields.add("schools");
        alFields.add("status");
        alFields.add("last_seen");
        alFields.add("followers_count");
        alFields.add("common_count");
        alFields.add("counters");
        alFields.add("occupation");
        alFields.add("nickname");
        alFields.add("relatives");
        alFields.add("relation");
        alFields.add("personal");
        alFields.add("connections");
        alFields.add("exports");
        alFields.add("wall_comments");
        alFields.add("activities");
        alFields.add("interests");
        alFields.add("music");
        alFields.add("movies");
        alFields.add("tv");
        alFields.add("books");
        alFields.add("games");
        alFields.add("about");
        alFields.add("quotes");
        alFields.add("can_post");
        alFields.add("can_see_all_posts");
        alFields.add("can_see_audio");
        alFields.add("can_write_private_message");
        alFields.add("can_send_friend_request");
        alFields.add("is_favorite");
        alFields.add("timezone");
        alFields.add("screen_name");
        alFields.add("maiden_name");
        alFields.add("crop_photo");
        alFields.add("is_friend");
        alFields.add("friend_status");

        ArrayList<ArrayList<String>> params = new ArrayList<ArrayList<String>>();
        params.add(alUser_id);
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
