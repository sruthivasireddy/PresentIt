package com.presentit.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class WelcomeActivity extends AppCompatActivity {

    ImageView imgVUserDisplayImage;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Intent intent = getIntent();
        GoogleSignInAccount user = (GoogleSignInAccount)intent.getExtras().getParcelable("USER_INFO");
        TextView tvUserDetails = (TextView) findViewById(R.id.txtUserDetails);
        tvUserDetails.setText(user.getDisplayName());

        imgVUserDisplayImage = (ImageView) findViewById(R.id.imgUserDisplayImage);
        Log.d("photo uri: ", user.getPhotoUrl().toString());
        String url = user.getPhotoUrl().toString();
        new loadImage().execute(url);
       // final Bitmap bitmap=null;

        //imgVUserDisplayImage.setImageBitmap(getBitmapFromURL(url));
        //imgVUserDisplayImage.setImageURI(user.getPhotoUrl());
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public class loadImage extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... urls) {
            // TODO Auto-generated method stub
            URL url;
            try {
                url = new URL(urls[0]);
                Log.d("url: ", url.toString());
                InputStream is=url.openStream();

                bitmap= BitmapFactory.decodeStream(is);

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //HttpURLConnection huc =(HttpURLConnection)url.openConnection();
            //huc.connect();
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
             imgVUserDisplayImage.setImageBitmap(bitmap);
        }
    }
}
