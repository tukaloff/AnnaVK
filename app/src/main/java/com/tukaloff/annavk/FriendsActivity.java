package com.tukaloff.annavk;

import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tukaloff.annavk.method.friends.Friends;
import com.tukaloff.annavk.method.photo.Photo;
import com.tukaloff.annavk.method.users.User;

import java.util.ArrayList;
import java.util.List;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class FriendsActivity extends ActionBarActivity implements OnClickListener, View.OnTouchListener {

    LinearLayout lvFriendsList;
    ArrayList<User> friends;
    ScrollView scrollFriends;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        scrollFriends = (ScrollView) findViewById(R.id.scrollFriends);
        scrollFriends.setOnTouchListener(this);
        lvFriendsList = (LinearLayout) findViewById(R.id.lvFriendsList);

        refresh();
    }

    private void refresh() {
        friends = sortByOnline(new Friends(30).getFriends());
        lvFriendsList.removeAllViews();
        LayoutInflater inflater = getLayoutInflater();
        for(int i = 0; i < friends.size(); i ++) {
            View item = inflater.inflate(R.layout.friend_item, lvFriendsList, false);
            TextView tvFriendName = (TextView) item.findViewById(R.id.tvFriendName);
            TextView tvFriendLastSeen = (TextView) item.findViewById(R.id.tvFriendLastSeen);
            ImageView ivFriendPhoto = (ImageView) item.findViewById(R.id.ivFriendPhoto);
            final int finalI = i;
            ivFriendPhoto.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //case (R.id.ivPhoto):
                    Intent intent = new Intent(v.getContext(), PhotoViewActivity.class);
                    intent.putExtra("Photo", new Photo(friends.get(finalI).getMainMaxPhotoURI()).getBitmap());
                    startActivity(intent);
                    //break;
                }
            });
            ivFriendPhoto.setImageBitmap(new Photo(friends.get(i).getMainPhotoURI()).getBitmap());
            tvFriendName.setText(friends.get(i).getFirstName() + " " + friends.get(i).getLastName());
            tvFriendLastSeen.setText(friends.get(i).toStringLastSeen());
            if (friends.get(i).getOnline() == 1) {
                tvFriendName.setBackgroundColor(Color.rgb(200, 250, 200));
                tvFriendLastSeen.setBackgroundColor(Color.rgb(200, 250, 200));
            } else {
                tvFriendName.setBackgroundColor(Color.LTGRAY);
                tvFriendLastSeen.setBackgroundColor(Color.LTGRAY);
            }
            TextView tvUser_id = (TextView) item.findViewById(R.id.tvUserId);
            tvUser_id.setBackgroundColor(Color.GRAY);
            item.setOnClickListener(this);
            item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            lvFriendsList.addView(item);
        }
    }

    private ArrayList<User> sortByOnline(ArrayList<User> friends) {
        ArrayList<User> online = new ArrayList<>(),
                offline = new ArrayList<>();
        for (int i = 0; i < friends.size(); i++) {
            if(friends.get(i).getOnline() == 1) {
                online.add(friends.get(i));
            } else {
                offline.add(friends.get(i));
            }
        }
        friends.clear();
        for(int i = 0; i < online.size(); i++) {
            friends.add(online.get(i));
        }
        for (int i = 0; i < offline.size(); i++) {
            friends.add(offline.get(i));
        }
        online.clear();
        offline.clear();
        return friends;
    }

    @Override
    public void onClick(View v) {
        String name = ((TextView)v.findViewById(R.id.tvFriendName)).getText().toString();
        for (int i = 0; i < friends.size(); i++) {
            if ((friends.get(i).getFirstName() + " " + friends.get(i).getLastName()).equals(name)) {
                Intent intent = new Intent(this, MessagesActivity.class);
                intent.putExtra("user_id", friends.get(i).getUser_id());
                startActivity(intent);
                break;
            }
        }
    }

    int scrollUp;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.scrollFriends:
                Log.d("LogTag", String.valueOf(v.getScrollY()));
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

    class FriendsAdapter extends ArrayAdapter<User> {

        public FriendsAdapter(Context context, int resource) {
            super(context, resource);
        }

        public FriendsAdapter(Context context, int resource, int textViewResourceId) {
            super(context, resource, textViewResourceId);
        }

        public FriendsAdapter(Context context, int resource, User[] objects) {
            super(context, resource, objects);
        }

        public FriendsAdapter(Context context, int resource, int textViewResourceId, User[] objects) {
            super(context, resource, textViewResourceId, objects);
        }

        public FriendsAdapter(Context context, int resource, List<User> objects) {
            super(context, resource, objects);
        }

        public FriendsAdapter(Context context, int resource, int textViewResourceId, List<User> objects) {
            super(context, resource, textViewResourceId, objects);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friends, menu);
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
}
