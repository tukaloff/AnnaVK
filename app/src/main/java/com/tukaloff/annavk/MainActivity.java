package com.tukaloff.annavk;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.tukaloff.annavk.method.message.Message;
import com.tukaloff.annavk.method.message.MessagesGet;
import com.tukaloff.annavk.method.message.MessagesLast;
import com.tukaloff.annavk.method.photo.Photo;
import com.tukaloff.annavk.method.users.User;
import com.tukaloff.annavk.networker.Connection;
import com.tukaloff.annavk.utils.UnixTimeParcer;

import org.w3c.dom.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends ActionBarActivity implements OnClickListener{

    public final String LOG_TAG = "LogTag";
    Button btnFriends, btnLastMsgs;
    TextView tvUser, tvUserLastSeen;
    User user;
    Bitmap img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, System.getProperty("user.name"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnFriends = (Button) findViewById(R.id.btnFriends);
        btnFriends.setOnClickListener(this);
        btnLastMsgs = (Button) findViewById(R.id.btnLastMsgs);
        btnLastMsgs.setOnClickListener(this);
        tvUser = (TextView) findViewById(R.id.tvUserName);
        tvUserLastSeen = (TextView) findViewById(R.id.tvUserLastSeen);
        String access_token;
        access_token = "Очень длинный токен";
        Connection.setAccessToken(access_token);
        user = getMe();
        tvUser.setText(user.getFirstName() + " " + user.getLastName());
        tvUserLastSeen.setText(user.toStringLastSeen());
        UnixTimeParcer ut = new UnixTimeParcer(user.getLastSeen());
        tvUserLastSeen.setText(ut.getSDateTTime());
        img = new Photo(user.getMainPhotoURI()).getBitmap();
        ImageView ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        ivPhoto.setImageBitmap(img);
        ivPhoto.setOnClickListener(this);
    }

    private User getMe() {
        User user = new User(76141154);
        return user;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
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
            case (R.id.btnFriends):
                getFriends();
                break;
            case (R.id.btnLastMsgs):
                //getLastMsgs();
                Intent intent = new Intent(this, DownloadImageActivity.class);
                startActivity(intent);
                break;
            case (R.id.ivPhoto):
                /*Intent*/ intent = new Intent(this, PhotoViewActivity.class);
                intent.putExtra("Photo", new Photo(user.getMainMaxPhotoURI()).getBitmap());
                startActivity(intent);
                break;
        }
    }

    private void getFriends() {
        Log.d(LOG_TAG, "getFriends");
        Intent intent = new Intent(this, FriendsActivity.class);
        startActivity(intent);
    }

    private void getLastMsgs() {
        Intent intent = new Intent(this, LastMessagesActivity.class);
        startActivity(intent);
    }
}
