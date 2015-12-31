package com.scienstechnologies.newsfeed.NewsPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.scienstechnologies.newsfeed.MainActivity;
import com.scienstechnologies.newsfeed.R;
import com.scienstechnologies.newsfeed.ShareActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.zip.Inflater;

import volley.AppController;

/**
 * Created by vamsitallapudi on 21-Dec-15.
 */
public class PageFragment extends Fragment {


    private static final String TAG = PageFragment.class.getSimpleName() ;
    TextView tvNewsHead;
    TextView tvNewsDetails;
    LinearLayout llFragmentPageBackground;
    String ourJsonString;

    TextView tvAuthor;
    TextView tvSource;
    TextView tvDate;
    LinearLayout llTotalText;
    ImageView ivCategoryIcon;

    // Doctors json url
    private static final String url = "http://webservices.sgssiddaheal.com/newsfeed/news/";

    public String jsonResponse;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_page, container, false);



        ImageView ivPageImage = (ImageView) rootView.findViewById(R.id.iv_page_image);

        tvNewsHead = (TextView) rootView.findViewById(R.id.tvNewsHead);
        tvNewsDetails = (TextView) rootView.findViewById(R.id.tvNewsDetails);
        tvAuthor = (TextView) rootView.findViewById(R.id.tvAuthor);
        tvSource = (TextView) rootView.findViewById(R.id.tvSource);
        tvDate = (TextView) rootView.findViewById(R.id.tvDate);
        llTotalText = (LinearLayout) rootView.findViewById(R.id.llTotalText);
        ivCategoryIcon = (ImageView) rootView.findViewById(R.id.ivCategoryIcon);

        ivPageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ShareActivity.class);
                startActivity(i);
            }
        });


        return rootView;
    }

    public void setFragmentBackground(){
        llFragmentPageBackground.setBackgroundColor(getResources().getColor(R.color.black));
    }
    public void setFragmentTextHeadingColor(){

        tvNewsHead.setTextColor(getResources().getColor(R.color.list_item_title));
    }

    public void setNews(int i){
        final int j = i;

        StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);

                try {

                    final Bundle args = getArguments();
                    String jsonString = args.getString("jsonData");
                    JSONObject object = new JSONObject(jsonString);
                    String message = object.getString("message");
                    Log.d(TAG, message);
                    String status = object.getString("status");

                    if (status.equals("success")) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        JSONObject jsonObject = jsonArray.getJSONObject(j);
                        tvNewsDetails.setText(jsonObject.getString("content"));
                        tvNewsHead.setText(jsonObject.getString("heading"));
                        tvAuthor.setText(jsonObject.getString("author"));
                        tvSource.setText(jsonObject.getString("source"));
                        tvDate.setText(jsonObject.getString("days"));

                        switch(Integer.parseInt(jsonObject.getString("category"))){
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

                        llTotalText.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {



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
            }
        });

        AppController.getInstance().addToRequestQueue(sr);


    }

}
