package com.tukaloff.annavk;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class PhotoViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        Bitmap img = (Bitmap)getIntent().getExtras().get("Photo");

        ImageView ivPhotoAct = (ImageView) findViewById(R.id.ivPhotoAct);
        ivPhotoAct.setImageBitmap(img);
    }
}
