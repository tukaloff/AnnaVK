package com.tukaloff.annavk.method.photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.tukaloff.annavk.networker.Connection;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by user on 20.06.2015.
 */
public class Photo {

    Bitmap img;

    Connection connection;

    public Photo(String URI) {
        connection = new Connection(URI);
        AsyncTask<String, Void, Bitmap> at = new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                publishProgress();
                try {
                    URL urlConn = new URL(params[0]);
                    InputStream input = urlConn.openStream();
                    return BitmapFactory.decodeStream(input);
                } catch (IOException e) {
                    e.printStackTrace();
                    return connection.readBitmap();
                }
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }


        };
        at.execute(URI);
        try {
            img = at.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getBitmap() {
        return img;
    }
}
