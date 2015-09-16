package com.tukaloff.annavk;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.tukaloff.annavk.method.message.Message;
import com.tukaloff.annavk.method.message.MessageHistory;

import java.util.ArrayList;
import android.view.View.OnClickListener;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MessagesActivity extends ActionBarActivity implements OnClickListener, View.OnTouchListener{

    int user_id;
    LinearLayout llHistory;
    EditText etSend;
    Button btnSend, btnMoreMsg;
    ScrollView scrollMessages;
    int offset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        etSend = (EditText) findViewById(R.id.etSend);
        //etSend.setText("android:text=ArrayList<String> alUser_id = new ArrayList<>();");
        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        btnMoreMsg = (Button) findViewById(R.id.btnMoreMsg);
        btnMoreMsg.setOnClickListener(this);

        scrollMessages = (ScrollView) findViewById(R.id.scrollMessages);

        scrollMessages.setOnTouchListener(this);

        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id", 76141154);

        llHistory = (LinearLayout) findViewById(R.id.llHistory);
        refresh();
    }

    private void refresh () {
        offset = 0;
        llHistory.removeAllViews();
        get();
    }

    private void get () {
        LayoutInflater inflater = getLayoutInflater();
        ArrayList<Message> msgHist = new MessageHistory(user_id, offset).get();
        for (int i = 0; i < msgHist.size(); i++){
            Message msg = msgHist.get(i);
            TextView msgBody, time, tvAttach;
            LinearLayout llAttach;
            View msgView;
            if (msg.isOut()) {
                msgView = inflater.inflate(R.layout.outmsg, llHistory, false);
                time = (TextView) msgView.findViewById(R.id.tvOutTime);
                msgBody = (TextView) msgView.findViewById(R.id.tvOutMsg);
                tvAttach = (TextView) msgView.findViewById(R.id.tvAttachOut);
                llAttach = (LinearLayout) msgView.findViewById(R.id.llAttachOut);
            } else {
                msgView = inflater.inflate(R.layout.inmsg, llHistory, false);
                time = (TextView) msgView.findViewById(R.id.tvInTime);
                msgBody = (TextView) msgView.findViewById(R.id.tvInMsg);
                tvAttach = (TextView) msgView.findViewById(R.id.tvAttachIn);
                llAttach = (LinearLayout) msgView.findViewById(R.id.llAttachIn);
            }
            time.setText(msg.getDateTime());
            Log.d("LogTag", msg.getBody());
            msgBody.setText(msg.getBody());
            if (msgBody.getText().equals(""))
                msgBody.setHeight(0);
            if (msg.haveAttachments()) {
                ArrayList<Object> attachments = msg.getAttach();
                for (int j = 0; j < attachments.size(); j++) {
                    Node node = (Node) attachments.get(j);
                    Log.d("LogTag", node.getNodeName());
                    Log.d("LogTag", node.getTextContent());
                    String text = node.getTextContent();
                    Log.d("LogTag", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    Log.d("LogTag", text);
                    tvAttach.setText(text);
                }
            } else {
                tvAttach.setHeight(0);
            }
            if (msg.readState()) {
                msgBody.setBackgroundColor(Color.LTGRAY);
            } else {
                msgBody.setBackgroundColor(Color.GRAY);
            }
            msgView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            llHistory.addView(msgView);
        }
        this.offset += msgHist.size();
    }

    private View addAttachment (LayoutInflater inflater,LinearLayout llAttach, NodeList nodeList) {
        View attachView = inflater.inflate(R.layout.wall_post, llAttach, false);
        TextView tvWallPost = (TextView) attachView.findViewById(R.id.tvWallPost);
        String text = "";
        for(int i = 0; i < nodeList.getLength(); i++)
            text += nodeList.item(i).getTextContent() + "\n";
        tvWallPost.setText(text);
        Log.d("LogTag", text);
        attachView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        return attachView;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_messages, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.btnSend):
                Message.send(user_id, etSend.getText().toString());
                etSend.setText("");
                refresh();
                break;
            case (R.id.btnMoreMsg):
                get();
                Toast.makeText(this, String.valueOf(offset), Toast.LENGTH_LONG).show();
                break;
        }
    }

    int scrollUp;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.scrollMessages:
                if (v.getScrollY() == 0) {
                    if (event.getRawY() < 300 && event.getAction() == 0) {
                        scrollUp = 0;
                    }
                    if (event.getRawY() >= 300 && event.getRawY() < 700
                            && event.getAction() == 2 && scrollUp == 0) {
                        scrollUp = 1;
                    }
                    if (event.getRawY() >= 700 && event.getAction() == 1 && scrollUp == 1) {
                        scrollUp = 2;
                    }
                    //Log.d("LogTag", String.valueOf(v.getScrollY()));
                    Log.d("LogTag", String.valueOf(scrollUp));
                    if (scrollUp == 2) {
                        refresh();
                        Toast.makeText(this, "hell yeah!", Toast.LENGTH_SHORT).show();
                        scrollUp = 0;
                    }
                }
                break;
        }
        return false;
    }
}
