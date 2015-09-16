package com.tukaloff.annavk.networker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by user on 09.05.2015.
 */
public class Connection {

    private String methodName;
    private String answerFormat;
    private ArrayList<ArrayList<String>> params;
    private String sParams;
    private static String accessToken;
    private String stringAnswer;
    private boolean success;
    private static DocumentBuilderFactory dbf;
    private String postget = "get";

    private String uri;

    public Connection(String URI) {
        uri = URI;
    }

    public Connection(String methodName, String answerFormat, ArrayList<ArrayList<String>> params, String postget) {
        dbf = DocumentBuilderFactory.newInstance();
        this.params = params;
        this.methodName = methodName;
        this.answerFormat = answerFormat;
        paramsToString();
        uri = "https://api.vk.com/method/" + methodName + answerFormat + "?" +
                sParams + "&" +
            "access_token=" + accessToken;
        this.postget = postget;
    }

    public Bitmap readBitmap() {
        URL url = null;
        try {
            url = new URL(uri);
            URLConnection uConn = url.openConnection();
            InputStream is = uConn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            Bitmap img = BitmapFactory.decodeStream((InputStream) new URL(uri).getContent());
            return img;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setAccessToken(String at) {
        accessToken = at;
    }

    private void paramsToString() {
        sParams = "";
        for (int i = 0; i < params.size(); i++) {
            for (int j = 0; j < params.get(i).size(); j++) {
                if (j == 0) {
                    sParams += params.get(i).get(j) + "=";
                } else {
                    if ((j + 1) != params.get(i).size()) {
                        sParams += params.get(i).get(j) + ",";
                    } else {
                        sParams += params.get(i).get(j);
                    }
                }
            }
            if ((i + 1) != params.size()) {
                sParams += "&";
            }
        }
    }

    private ArrayList<String[]> paramsToPair() {
        ArrayList<String[]> pairArray = new ArrayList<>();
        for (int i = 0; i < params.size(); i++) {
            String[] pair = new String[2];
            for (int j = 0; j < params.get(i).size(); j++) {
                if(j == 0) {
                    pair[0] = params.get(i).get(j);
                } else {
                    if (j == params.get(i).size() - 1) {
                        pair[1] += params.get(i).get(j);
                    } else {
                        pair[1] += params.get(i).get(j) + ",";
                    }
                }
            }
            pairArray.add(pair);
        }
        return pairArray;
    }

    public void exec() {
        AsyncTask<String, Void, String> asTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    stringAnswer = "";
                    URL url = new URL(params[0]);
                    URLConnection uConn = url.openConnection();
                    if (params[1].equals("post")) {
                        URL postUrl = new URL(params[3]);
                        HttpsURLConnection connection = (HttpsURLConnection) postUrl.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setDoOutput(true);
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                        bw.write(params[2] + "&" + "access_token=" + params[4]);
                        bw.flush();
                        bw.close();
                        BufferedReader br = new BufferedReader(new InputStreamReader(
                                connection.getInputStream()
                        ));
                        String line = "";
                        while ((line = br.readLine()) != null) {
                            stringAnswer += line;
                        }

                    } else {
                        InputStreamReader in = new InputStreamReader(uConn.getInputStream());
                        BufferedReader bufReader = new BufferedReader(in);
                        String line = "";
                        while ((line = bufReader.readLine()) != null) {
                            stringAnswer += line;
                        }
                        bufReader.close();
                    }
                    success = true;
                } catch (Exception e) {
                    stringAnswer = e.getLocalizedMessage();
                    success = false;
                }
                //Log.d("LogTag", stringAnswer);
                return stringAnswer;
            }

            public void post() throws Exception{

            }
        };

        try {
            Log.d("LogTag", "before: " + methodName + answerFormat);
            stringAnswer = asTask.execute(new String[]{uri, postget, sParams,
                    "https://api.vk.com/method/" + methodName + answerFormat, accessToken}).get();
            if (stringAnswer.length() > 5000) {
                Log.d("LogTag", "after: " + stringAnswer.substring(0, 5000));
                Log.d("LogTag", "after: " + stringAnswer.substring(5001, stringAnswer.length()));
            }
        } catch (Exception e) {
            Log.d("LogTag", "Error" + e.getMessage());
        }
    }

    public String getStringAnswer() {
        return stringAnswer;
    }

    public Document getXMLAnswer() {
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new ByteArrayInputStream(stringAnswer.getBytes()));
            return doc;
        } catch (Exception e) {
            return null;
        }
    }
}
