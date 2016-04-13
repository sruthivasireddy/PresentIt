package com.presentit.app;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private GoogleSignInAccount user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Intent intent = getIntent();
        user = intent.getExtras().getParcelable("USER_INFO");
        TextView tvUserDetails = (TextView) findViewById(R.id.txtUserDetails);
        Log.d("STATE", "Authenticated User Display Name:" + user.getDisplayName());
        if (tvUserDetails != null) {
            tvUserDetails.setText(user.getDisplayName());
        }

        imgVUserDisplayImage = (ImageView) findViewById(R.id.imgUserDisplayImage);
        if(user.getPhotoUrl() != null) {
            Log.d("photo uri: ", user.getPhotoUrl().toString());
            String url = user.getPhotoUrl().toString();
            new loadImage().execute(url);
        }

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList = (ListView)findViewById(R.id.navList);
        if(mDrawerList==null)
            Log.d("ERROR", null);

        mActivityTitle = user.getDisplayName();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

       // mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        getSupportActionBar().setTitle(mActivityTitle);

        // final Bitmap bitmap=null;

        //imgVUserDisplayImage.setImageBitmap(getBitmapFromURL(url));
        //imgVUserDisplayImage.setImageURI(user.getPhotoUrl());

        //PresentItApi.Builder builder = new PresentItApi.Builder(
                //AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
        //service = builder.build();
        //credential = GoogleAccountCredential.usingAudience(this,"server:client_id:971441787328-0hdp9pajsejqsj9ir15oha2e25vjvv4s.apps.googleusercontent.com");

    }

    private void addDrawerItems() {
        String[] menuArray = { "Profile", "Courses", "Classrooms" };
        mAdapter = new ArrayAdapter<>(this,R.layout.drawer_listview_item , menuArray);
        mDrawerList.setAdapter(mAdapter);

        /* TODO: why to add these 2 lines */
        mDrawerList.bringToFront();
        mDrawerLayout.requestLayout();

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Options");
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(user.getDisplayName());
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

        // Activate the navigation drawer toggle
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Toast.makeText(WelcomeActivity.this, ((TextView)view).getText(), Toast.LENGTH_LONG).show();
            //mDrawerLayout.closeDrawer(mDrawerList);
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
