package com.treuddm.AppyNews.Daily;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.treuddm.AppyNews.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import volley.AppController;

public class DailyTitbit extends AppCompatActivity {

    private static final String TAG = "DailyTitbit.java";
    ProgressDialog mProgressDialog;
    TextView tvDailyTitbitDetail, tvDailyTitbitShare;
    String url = "http://webservices.sgssiddaheal.com/newsfeed/day_feed";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
// Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }


        setContentView(R.layout.activity_daily_titbit);


        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

        ImageView ivClose = (ImageView) findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvDailyTitbitDetail = (TextView) findViewById(R.id.tvTitbitDetail);

        tvDailyTitbitShare = (TextView) findViewById(R.id.tvDailyTitbitShare);

        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                hidePDialog();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        JSONObject quoteObject = jsonArray.getJSONObject(2);
                        tvDailyTitbitDetail.setText(quoteObject.getString("day_feed_text"));

                        tvDailyTitbitShare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                try {
                                    File imageFile = takeScreenshot();
                                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                    Uri screenshotUri = Uri.fromFile(imageFile);

                                    sharingIntent.setType("*/*");

                                    sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Download Android app at: https://play.google.com/store/apps/details?id=com.treuddm.appynews");
                                    startActivity(Intent.createChooser(sharingIntent, "Share image using"));
                                } catch (Exception e) {
                                    Toast.makeText(DailyTitbit.this, e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
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
                hidePDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(sr);

    }


    private void hidePDialog() {

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }


    private File takeScreenshot() {

        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);
            // image naming and path  to include sd card  appending name you choose for file

            String pathToImage = Environment.getExternalStorageDirectory().getPath() + "/" + now + ".jpg";
            File imageFile = new File(pathToImage);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            Toast.makeText(this, "Screenshot Saved", Toast.LENGTH_LONG).show();

            return imageFile;

        } catch (Throwable e) {
            // Several error may come out with file handling or OOM


            Toast.makeText(this, "Error saving image! Please check if SDCard is properly inserted", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        }


    }

}
