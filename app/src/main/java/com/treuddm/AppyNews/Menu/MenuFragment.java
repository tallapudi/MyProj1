package com.treuddm.AppyNews.Menu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.treuddm.AppyNews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vamsitallapudi on 06-Jan-16.
 */
public class MenuFragment extends Fragment {

    private List<CategoryModel> mCategoryList = new ArrayList<>();
    private CategoryListAdapter mCategoryListAdapter;

    private ListView mListView;

    String[] mCategories = new String[]{
            "Bookmarked", "All", "Top", "National",
            "International", "Politics", "Business", "Sports",
            "Entertainment", "Daily Quotes", "Event of the day", "Daily Titbit",
            "Personalization"
    };

    int[] mIcons = new int[]{
            R.drawable.bookmark_icon, R.drawable.all_news_icon, R.drawable.top_icon,
            R.drawable.national_icon, R.drawable.international_icon, R.drawable.politics_icon,
            R.drawable.business_icon, R.drawable.sports_icon, R.drawable.bookmark_icon,
            R.drawable.all_news_icon, R.drawable.top_icon, R.drawable.national_icon,
            R.drawable.international_icon, R.drawable.politics_icon, R.drawable.business_icon,
            R.drawable.sports_icon, R.drawable.bookmark_icon, R.drawable.all_news_icon,
            R.drawable.top_icon, R.drawable.national_icon, R.drawable.international_icon
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_menu, container, false);
        mListView = (ListView) v.findViewById(R.id.list);
        //Custom ListAdapter for our android custom listView
        mCategoryListAdapter = new CategoryListAdapter(getActivity(), mCategoryList);
        mListView.setAdapter(mCategoryListAdapter);


        if(mCategoryList.isEmpty()){
            for (int i = 0; i < 7; i++) {
                CategoryModel categoryModel = new CategoryModel();
                categoryModel.setCategoryName(mCategories[i]);
                categoryModel.setIcon(mIcons[i]);

                mCategoryList.add(categoryModel);
            }
            CategoryModel categoryModel = new CategoryModel();
            categoryModel.setCategoryName("+More");
            mCategoryList.add(categoryModel);

            //Open a new activity when a list item from android custom listview is clicked.
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    if (position == 7) {
                        mCategoryList.clear();


                        for (int i = 0; i < mCategories.length; i++) {
                            CategoryModel categoryModel = new CategoryModel();
                            categoryModel.setCategoryName(mCategories[i]);
                            categoryModel.setIcon(mIcons[i]);
                            mCategoryList.add(categoryModel);
                        }

                        SharedPreferences urlPref = getActivity().getSharedPreferences("urlPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor= urlPref.edit();
                        editor.putString("url", "http://webservices.sgssiddaheal.com/newsfeed/news/");
                        editor.commit();
                        mCategoryListAdapter.notifyDataSetChanged();
                    }
                    else{

                        SharedPreferences urlPref = getActivity().getSharedPreferences("urlPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor= urlPref.edit();
                        switch(position){

                            case 0:
                                editor.putString("url","C");
                                editor.commit() ;
                                getActivity().finish();
                                break;

                            case 1:
                                editor.putString("url", "http://webservices.sgssiddaheal.com/newsfeed/news/");
                                editor.commit();
                                getActivity().finish();
                                break;
                            case 2:

                                editor.putString("url", "http://webservices.sgssiddaheal.com/newsfeed/news/1");
                                editor.commit();
                                getActivity().finish();
                                break;

                            case 3:
                                editor.putString("url", "http://webservices.sgssiddaheal.com/newsfeed/news/2");
                                editor.commit();
                                getActivity().finish();
                                break;
                            case 4:
                                editor.putString("url", "http://webservices.sgssiddaheal.com/newsfeed/news/3");
                                editor.commit();
                                getActivity().finish();
                                break;
                            case 5:
                                editor.putString("url", "http://webservices.sgssiddaheal.com/newsfeed/news/4");
                                editor.commit();
                                getActivity().finish();
                                break;
                            case 6:
                                editor.putString("url", "http://webservices.sgssiddaheal.com/newsfeed/news/5");
                                editor.commit();
                                getActivity().finish();
                                break;
                            case 7:
                                editor.putString("url", "http://webservices.sgssiddaheal.com/newsfeed/news/6");
                                editor.commit();
                                getActivity().finish();
                                break;
                            case 8:
                                editor.putString("url", "http://webservices.sgssiddaheal.com/newsfeed/news/7");
                                editor.commit();
                                getActivity().finish();
                                break;
                            case 9:
                                editor.putString("url", "http://webservices.sgssiddaheal.com/newsfeed/news/8");
                                editor.commit();
                                getActivity().finish();
                                break;
                            case 10:
                                editor.putString("url", "http://webservices.sgssiddaheal.com/newsfeed/news/9");
                                editor.commit();
                                getActivity().finish();
                                break;
                            case 11:
                                editor.putString("url", "http://webservices.sgssiddaheal.com/newsfeed/news/10");
                                editor.commit();
                                getActivity().finish();
                                break;
                            case 12:
                                editor.putString("url", "http://webservices.sgssiddaheal.com/newsfeed/news/11");
                                editor.commit();
                                getActivity().finish();
                                break;
                            case 13:
                                editor.putString("url", "http://webservices.sgssiddaheal.com/newsfeed/news/12");
                                editor.commit();
                                getActivity().finish();
                                break;
                            default:

                                editor.putString("url", "http://webservices.sgssiddaheal.com/newsfeed/news/newsfeed/news/");
                                editor.commit();
                                getActivity().finish();
                        }
                    }

                    SharedPreferences sharedpreferences = getActivity().getSharedPreferences("urlPref", Context.MODE_PRIVATE);
                    String myUrlString = sharedpreferences.getString("url","Shared Pref Not Stored");
                }
            });

        }








        return v;
    }


}
