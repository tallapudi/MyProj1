package com.treuddm.AppyNews;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gcm.GCMRegistrar;
import com.treuddm.AppyNews.GCM.AlertDialogManager;
import com.treuddm.AppyNews.GCM.ConnectionDetector;
import com.treuddm.AppyNews.GCM.ServerUtilities;
import com.treuddm.AppyNews.GCM.WakeLocker;
import com.treuddm.AppyNews.Menu.MenuActivity;
import com.treuddm.AppyNews.Menu.SettingsFragment;
import com.treuddm.AppyNews.NewsPage.PageFragment;
import com.treuddm.AppyNews.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import volley.AppController;


import static com.treuddm.AppyNews.GCM.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.treuddm.AppyNews.GCM.CommonUtilities.EXTRA_MESSAGE;
import static com.treuddm.AppyNews.GCM.CommonUtilities.SENDER_ID;



public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    /**
     * The number of pages.
     */

    // label to display gcm messages
    TextView lblMessage;

    // Asyntask
    AsyncTask<Void, Void, Void> mRegisterTask;

    // Alert dialog manager
    AlertDialogManager alert = new AlertDialogManager();

    // Connection detector
    ConnectionDetector cd;

    public static String name = "AppyNews";
    public static String email = "appynewsinfo@gmail.com";

    private int num_pages = 1;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */

    private ViewPager mViewPager;

    Context mContext;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */

    private PagerAdapter mPagerAdapter;


    private CharSequence mTitle;
    private CharSequence mDrawerTitle;

    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    public Map<Integer, PageFragment> mPageReferenceMap = new HashMap<Integer, PageFragment>();
    public static String myJsonString;

    ImageView ivRefreshIcon;
    ImageView ivMenuIcon;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {


        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(MainActivity.this,
                    "Internet Connection Error",
                    "Please connect to Internet", false);
            // stop executing code by return
            return;
        }





        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(this);


        registerReceiver(mHandleMessageReceiver, new IntentFilter(
                DISPLAY_MESSAGE_ACTION));

        // Get GCM registration id
        final String regId = GCMRegistrar.getRegistrationId(this);

        // Check if regid already presents
        if (regId.equals("")) {
            // Registration is not present, register now with GCM
            GCMRegistrar.register(this, SENDER_ID);
        } else {
            // Device is already registered on GCM
            if (GCMRegistrar.isRegisteredOnServer(this)) {
                // Skips registration.
                Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
            } else {
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.
                final Context context = this;
                mRegisterTask = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        // Register on our server
                        // On server creates a new user
                        ServerUtilities.register(context, name, email, regId);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        mRegisterTask = null;
                    }

                };
                mRegisterTask.execute(null, null, null);
            }
        }








        //instantiate a viewpager and a pageradapter
        mViewPager = (ViewPager) findViewById(R.id.pager);
        ivMenuIcon = (ImageView) findViewById(R.id.ivMenu);
        ivRefreshIcon = (ImageView) findViewById(R.id.ivRefresh);

        ivMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });


        ivRefreshIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences urlPref = getSharedPreferences("urlPref", Context.MODE_PRIVATE);

                String url = urlPref.getString("url", "http://webservices.sgssiddaheal.com/newsfeed/news/");

                callDataFromNetwork(url);

            }
        });


        SharedPreferences urlPref = getSharedPreferences("urlPref", Context.MODE_PRIVATE);

        String url = urlPref.getString("url", "http://webservices.sgssiddaheal.com/newsfeed/news/");


        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                myJsonString = response;

                try {
                    JSONObject object = new JSONObject(response);
                    Intent i = new Intent();

                    String message = object.getString("message");
                    Log.d(TAG, message);
                    String status = object.getString("status");

                    if (status.equals("success")) {

                        Bundle bundle = new Bundle();
                        bundle.putString("jsonData", response);
                        JSONArray jsonArray = object.getJSONArray("data");
                        num_pages = jsonArray.length();
                        myJsonString = response;

                        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), bundle);
                        mViewPager.setAdapter(mPagerAdapter);

                        SettingsFragment settingsFragment = new SettingsFragment();
                        mPagerAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG, "" + error.getMessage() + "," + error.toString());
                Toast.makeText(MainActivity.this, "Network Request Timeout!", Toast.LENGTH_LONG).show();
            }
        });

        AppController.getInstance().addToRequestQueue(sr);


    }





    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }


    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(int position) {
        // update the main content by replacing fragments

        int index = mViewPager.getCurrentItem();
        ScreenSlidePagerAdapter adapter = (ScreenSlidePagerAdapter) mViewPager.getAdapter();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        switch (item.getItemId()) {
            case R.id.action_bar_notification:

                return true;
            case R.id.action_nightmode:
                if (item.isChecked()) {

                    item.setChecked(false);
//                    CoordinatorLayout coordinatorLayout =(CoordinatorLayout) findViewById(R.id.coordinator_main);
//                    coordinatorLayout.setBackgroundColor(getResources().getColor(R.color.list_item_title));
                    int index = mViewPager.getCurrentItem();
                    ScreenSlidePagerAdapter adapter = (ScreenSlidePagerAdapter) mViewPager.getAdapter();
                    PageFragment fragment = adapter.getFragment(index);
                    fragment.setFragmentBackgroundDaymode();
                    fragment.setFragmentTextColorDaymode();

                } else {
                    item.setChecked(true);
                    int index = mViewPager.getCurrentItem();
                    ScreenSlidePagerAdapter adapter = (ScreenSlidePagerAdapter) mViewPager.getAdapter();
                    PageFragment fragment = adapter.getFragment(index);
                    fragment.setFragmentBackgroundNightmode();
                    fragment.setFragmentTextColorNightmode();
                }
                return true;
            case R.id.action_likethisapp:
                return true;
            case R.id.action_sharethisapp:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment Objects, in sequence.
     */
    public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {


        private final Bundle fragmentBundle;

        public ScreenSlidePagerAdapter(FragmentManager fm, Bundle data) {
            super(fm);
            fragmentBundle = data;
        }

        @Override
        public Fragment getItem(int position) {

            //Log.d(TAG, String.valueOf(position));

            PageFragment pageFragment = new PageFragment();
            mPageReferenceMap.put(position, pageFragment);
            pageFragment.setArguments(this.fragmentBundle);
            String news_url = "http://webservices.sgssiddaheal.com/newsfeed/news/";
            pageFragment.setNews(position, news_url);


            return pageFragment;
        }

        @Override
        public int getCount() {

            return num_pages;

        }

        public PageFragment getFragment(int key) {
            return mPageReferenceMap.get(key);
        }



        public Fragment getActiveFragment(ViewPager container, int position) {

            String name = makeFragmentName(container.getId(), position);
            return getSupportFragmentManager().findFragmentByTag(name);
        }

        private String makeFragmentName(int viewId, int index) {
            return "android:switcher:" + viewId + ":" + index;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            mPageReferenceMap.remove(position);
        }
    }


    /***
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences urlPref = getSharedPreferences("urlPref", Context.MODE_PRIVATE);

        String url = urlPref.getString("url", "http://webservices.sgssiddaheal.com/newsfeed/news/");

        if (!url.equals("http://webservices.sgssiddaheal.com/newsfeed/news/")) {
            callDataFromNetwork(url);
        }


    }

    private void callDataFromNetwork(String url) {
        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
                myJsonString = response;

                try {
                    JSONObject object = new JSONObject(response);
                    Intent i = new Intent();

                    String message = object.getString("message");
                    Log.d(TAG, message);
//                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                    String status = object.getString("status");

                    if (status.equals("success")) {
//                        Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_LONG).show();

                        Bundle bundle = new Bundle();
                        bundle.putString("jsonData", response);
                        JSONArray jsonArray = object.getJSONArray("data");
                        num_pages = jsonArray.length();
                        myJsonString = response;


                        Toast.makeText(getApplicationContext(), num_pages + " Articles Found!", Toast.LENGTH_LONG).show();

                        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), bundle);
                        mViewPager.setAdapter(mPagerAdapter);

                       // SettingsFragment settingsFragment = new SettingsFragment();
                        mPagerAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG, "" + error.getMessage() + "," + error.toString());
                Toast.makeText(MainActivity.this, "Network Request Timeout!", Toast.LENGTH_LONG).show();
            }
        });

        AppController.getInstance().addToRequestQueue(sr);
    }


    /**
     * Receiving push messages
     * */
    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
            // Waking up mobile if it is sleeping
            WakeLocker.acquire(getApplicationContext());

            /**
             * Take appropriate action on this message
             * depending upon your app requirement
             * For now i am just displaying it on the screen
             * */

            // Showing received message
            lblMessage.append(newMessage + "\n");
            Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();

            // Releasing wake lock
            WakeLocker.release();
        }
    };



    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {

        if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
        }
        try {
            unregisterReceiver(mHandleMessageReceiver);
            GCMRegistrar.onDestroy(this);
        } catch (Exception e) {
            Log.e("UnReg Receiver Error", "> " + e.getMessage());
        }


        super.onDestroy();
        SharedPreferences urlPref = getSharedPreferences("urlPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = urlPref.edit();
        editor.clear();
        editor.commit();
    }
}