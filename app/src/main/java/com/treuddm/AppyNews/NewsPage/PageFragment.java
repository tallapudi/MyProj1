package com.treuddm.AppyNews.NewsPage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.treuddm.AppyNews.Daily.DailyEvent;
import com.treuddm.AppyNews.Daily.DailyQuote;
import com.treuddm.AppyNews.Daily.DailyTitbit;
import com.treuddm.AppyNews.R;
import com.treuddm.AppyNews.WebView.WebViewActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import volley.AppController;

/**
 * Created by vamsitallapudi on 21-Dec-15.
 */
public class PageFragment extends Fragment {


    private static final String TAG = PageFragment.class.getSimpleName();
    TextView tvNewsHead;
    TextView tvNewsDetails;
    LinearLayout llFragmentPageBackground;
    String ourJsonString;

    TextView tvAuthor;
    TextView tvSource;
    TextView tvDate;
    TextView tvBy;
    private ProgressDialog mProgressDialog;
    LinearLayout llTotalText;
    RelativeLayout rlContent;
    ImageView ivCategoryIcon;
    ImageView ivPageImage;
    ImageView ivPlay;
    // Doctors json url
    private static final String url = "http://webservices.sgssiddaheal.com/newsfeed/news/";

    public String jsonResponse;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_page, container, false);


        Context mContext = getContext();
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage("Loading..");
        mProgressDialog.show();

        ivPageImage = (ImageView) rootView.findViewById(R.id.iv_page_image);
        ivPlay = (ImageView) rootView.findViewById(R.id.ivPlay);

        tvBy = (TextView) rootView.findViewById(R.id.tvBy);
        tvBy.setText("By ");
        tvNewsHead = (TextView) rootView.findViewById(R.id.tvNewsHead);
        tvNewsDetails = (TextView) rootView.findViewById(R.id.tvNewsDetails);
        tvAuthor = (TextView) rootView.findViewById(R.id.tvAuthor);
        tvSource = (TextView) rootView.findViewById(R.id.tvSource);
        tvDate = (TextView) rootView.findViewById(R.id.tvDate);
        llTotalText = (LinearLayout) rootView.findViewById(R.id.llTotalText);
        rlContent = (RelativeLayout) rootView.findViewById(R.id.rlContent);
        ivCategoryIcon = (ImageView) rootView.findViewById(R.id.ivCategoryIcon);
        llFragmentPageBackground = (LinearLayout) rootView.findViewById(R.id.llfragmentpagebackground);




        return rootView;
    }


    private File takeScreenshot() {

        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {

            // create bitmap screen capture
            View v1 = getActivity().getWindow().getDecorView().getRootView();
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

            Toast.makeText(getActivity(), "Screenshot Saved", Toast.LENGTH_LONG).show();

            return imageFile;

        } catch (Throwable e) {
            // Several error may come out with file handling or OOM


            Toast.makeText(getActivity(), "Error saving image! Please check if SDCard is properly inserted", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        }


    }


    @Override
    public void onResume() {
        super.onResume();


        SharedPreferences sharedPrefs = getActivity().getSharedPreferences("NightMode", Context.MODE_PRIVATE);
        Boolean nightMode = sharedPrefs.getBoolean("nightmode", false);

        if (nightMode) {
            setFragmentTextColorNightmode();
            setFragmentBackgroundNightmode();
        } else {
            setFragmentTextColorDaymode();
            setFragmentBackgroundDaymode();
        }

    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }



    private void shareImage(File file) {
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Share Screenshot"));
    }


    public void setFragmentBackgroundNightmode() {
        llFragmentPageBackground.setBackgroundColor(getResources().getColor(R.color.black));
    }

    public void setFragmentTextColorNightmode() {

        tvNewsHead.setTextColor(getResources().getColor(R.color.white));
        tvNewsDetails.setTextColor(getResources().getColor(R.color.white));
        tvAuthor.setTextColor(getResources().getColor(R.color.white));
        tvSource.setTextColor(getResources().getColor(R.color.white));
        tvDate.setTextColor(getResources().getColor(R.color.white));
        tvBy.setTextColor(getResources().getColor(R.color.white));

    }


    public void setFragmentBackgroundDaymode() {
        llFragmentPageBackground.setBackgroundColor(getResources().getColor(R.color.white));
    }

    public void setFragmentTextColorDaymode() {

        tvNewsHead.setTextColor(getResources().getColor(R.color.black));
        tvNewsDetails.setTextColor(getResources().getColor(R.color.text_color_cement));
        tvAuthor.setTextColor(getResources().getColor(R.color.black));
        tvSource.setTextColor(getResources().getColor(R.color.text_color_cement));
        tvDate.setTextColor(getResources().getColor(R.color.text_color_cement));
        tvBy.setTextColor(getResources().getColor(R.color.text_color_cement));
    }


    public void setNews(int i, String news_url) {



        final int j = i;




        StringRequest sr = new StringRequest(Request.Method.GET, news_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);

                hidePDialog();

                try {

                    final Bundle args = getArguments();
                    String jsonString = args.getString("jsonData");
                    final JSONObject object = new JSONObject(jsonString);
                    String message = object.getString("message");
                    Log.d(TAG, message);
                    String status = object.getString("status");
                    if (status.equals("success")) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        final JSONObject jsonObject = jsonArray.getJSONObject(j);
                        tvNewsDetails.setText(jsonObject.getString("content"));
                        tvNewsHead.setText(jsonObject.getString("heading"));
                        tvAuthor.setText(jsonObject.getString("author"));
                        tvSource.setText(jsonObject.getString("source"));
                        tvDate.setText(jsonObject.getString("days"));

                        Picasso.with(getContext()).load("http://www.sgssiddaheal.com/sciens_dashboard/" + jsonObject.getString("image_path")).error(R.string.com_facebook_image_download_unknown_error).into(ivPageImage);

                        if (jsonObject.getString("video_link").length() == 0) {
                            ivPlay.setVisibility(View.GONE);
                        }

                        

                        final String sourceLink = jsonObject.getString("source_link");
                        final String videoLink = jsonObject.getString("video_link");


                        ivPageImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                try {

                                    File imageFile = takeScreenshot();
                                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                    Uri screenshotUri = Uri.fromFile(imageFile);

                                    sharingIntent.setType("*/*");

                                    sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Read more at: "+ sourceLink);
                                    startActivity(Intent.createChooser(sharingIntent, "Share image using"));
                                } catch (Exception e) {
                                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                                }

//                Intent i = new Intent(getActivity(), ShareActivity.class);
//                i.putExtra("pathToImage", pathToImage);
//                startActivity(i);
                            }
                        });


                        if( j==0){

                            SharedPreferences dailyQuotePref = getActivity().getSharedPreferences("dailyQuotePref", Context.MODE_PRIVATE);

                            long prevTime = dailyQuotePref.getLong("dailyQuoteTime", 0);

                            long currentTime = System.currentTimeMillis();
                            if(currentTime-prevTime > 86400000){
                                SharedPreferences.Editor editor = dailyQuotePref.edit();
                                editor.putLong("dailyQuoteTime",currentTime);
                                editor.commit();
                                Intent intent = new Intent(getActivity(), DailyQuote.class);
                                startActivity(intent);
                            }
                        }
                        if(j==4){
                            SharedPreferences dailyEventPref = getActivity().getSharedPreferences("dailyEventPref", Context.MODE_PRIVATE);
                            long prevTime = dailyEventPref.getLong("dailyEventTime",0);
                            long currentTime = System.currentTimeMillis();
                            if(currentTime - prevTime > 86400000){
                                SharedPreferences.Editor editor = dailyEventPref.edit();
                                editor.putLong("dailyEventTime",currentTime);
                                editor.commit();
                                Intent intent = new Intent(getActivity(),DailyEvent.class);
                                startActivity(intent);
                            }

                        }
                        if(j== 9){
                            SharedPreferences dailyTitbitPref = getActivity().getSharedPreferences("dailyQuotePref", Context.MODE_PRIVATE);
                            long prevTime = dailyTitbitPref.getLong("dailyTitbitTime", 0);
                            long currentTime = System.currentTimeMillis();

                            if(currentTime - prevTime> 86400000){
                                SharedPreferences.Editor editor = dailyTitbitPref.edit();
                                editor.putLong("dailyTitbitTime",currentTime);
                                editor.commit();
                                Intent intent = new Intent(getActivity(), DailyTitbit.class);
                                startActivity(intent);

                            }
                        }

                        llTotalText.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent i = new Intent(getContext(), WebViewActivity.class);

                                Log.i(TAG, sourceLink);
                                i.putExtra("link", sourceLink);
                                startActivity(i);

                            }
                        });


                        ivPlay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent i = new Intent(getActivity(), WebViewActivity.class);
                                i.putExtra("link", videoLink);
                                startActivity(i);

                            }
                        });


                        switch (Integer.parseInt(jsonObject.getString("category"))) {
                            case 1:
                                ivCategoryIcon.setImageResource(R.drawable.bookmark_icon);
                                break;
                            case 2:
                                ivCategoryIcon.setImageResource(R.drawable.all_news_icon);
                                break;
                            case 3:
                                ivCategoryIcon.setImageResource(R.drawable.top_icon);
                                break;
                            case 4:
                                ivCategoryIcon.setImageResource(R.drawable.national_icon);
                                break;
                            case 5:
                                ivCategoryIcon.setImageResource(R.drawable.international_icon);
                                break;
                            case 6:
                                ivCategoryIcon.setImageResource(R.drawable.politics_icon);
                                break;
                            case 7:
                                ivCategoryIcon.setImageResource(R.drawable.business_icon);
                                break;
                            case 8:
                                ivCategoryIcon.setImageResource(R.drawable.sports_icon);
                                break;
                            default:
                                ivCategoryIcon.setImageResource(R.drawable.international_icon);
                                break;
                        }


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


}
