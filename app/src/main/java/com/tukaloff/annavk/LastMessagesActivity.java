package com.tukaloff.annavk;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tukaloff.annavk.method.message.Message;
import com.tukaloff.annavk.method.message.MessagesLast;
import com.tukaloff.annavk.method.users.User;

import java.util.ArrayList;


public class LastMessagesActivity extends ActionBarActivity implements View.OnClickListener, View.OnTouchListener{

    LinearLayout llLastMessages;
    ArrayList<Message> lastMsgs;
    ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_messages);

        llLastMessages = (LinearLayout) findViewById(R.id.llLastMessages);

        refresh();
    }


    private void refresh() {
        lastMsgs = new MessagesLast().MessagesLast();
        llLastMessages.removeAllViews();
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < lastMsgs.size(); i++) {
            View view = inflater.inflate(R.layout.lastmsg, llLastMessages, false);
            TextView tvFrom = (TextView) view.findViewById(R.id.tvFrom);
            TextView tvLastTime = (TextView) view.findViewById(R.id.tvLastTime);
            TextView tvBody = (TextView) view.findViewById(R.id.tvBody);
            Message msg = lastMsgs.get(i);
            User user = null;
            int usersSize = users.size();
            Log.d("LogTag", "in");
            if (usersSize > 0) {
                boolean eq = false;
                for (int k = 0; k < usersSize; k++) {
                    if (users.get(k).getUser_id() == msg.getUid()) {
                        user = users.get(k);
                        eq = true;
                        break;
                    }
                }
                if (!eq) {
                    user = new User(msg.getUid());
                    users.add(user);
                }
            } else {
                user = new User(msg.getUid());
                users.add(user);
            }
            tvFrom.setText(user.getFirstName() + " " + user.getLastName());
            Log.d("LogTag", "out");
            if (user.getOnline()==1)
                tvFrom.setBackgroundColor(Color.rgb(200, 250, 200));

            tvLastTime.setText(msg.getDateTime());
            tvBody.setText(msg.getBody());
            if (!msg.readState()) {
                tvBody.setBackgroundColor(Color.GRAY);
            }
            view.setOnClickListener(this);
            view.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            llLastMessages.addView(view);
        }
    }


    @Override
              public boolean onCreateOptionsMenu(Menu menu) {
                  // Inflate the menu; this adds items to the action bar if it is present.
                  getMenuInflater().inflate(R.menu.menu_last_messages, menu);
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
                  String name = ((TextView)v.findViewById(R.id.tvFrom)).getText().toString();
                  Log.d("LogTag", name);
                  for (int i = 0; i < users.size(); i++) {
                      if ((users.get(i).getFirstName() + " " + users.get(i).getLastName()).equals(name)) {
                          Log.d("LogTag", users.get(i).getFirstName() + " " + users.get(i).getLastName());
                          Intent intent = new Intent(this, MessagesActivity.class);
                          Log.d("LogTag", String.valueOf(users.get(i).getUser_id()));
                          Log.d("LogTag", String.valueOf(users.get(i).getUserString()));
                          intent.putExtra("user_id", users.get(i).getUser_id());
                          startActivity(intent);
                          break;
                      }
                  }
              }

    @Override
              public boolean onTouch(View v, MotionEvent event) {
                  return false;
              }
}
