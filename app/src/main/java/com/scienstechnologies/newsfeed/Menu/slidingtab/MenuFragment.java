package com.scienstechnologies.newsfeed.Menu.slidingtab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.scienstechnologies.newsfeed.Menu.Category;
import com.scienstechnologies.newsfeed.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vamsitallapudi on 06-Jan-16.
 */
public class MenuFragment extends Fragment {

    private List<Category> mCategoryList = new ArrayList<>();
    private CategoryListAdapter mCategoryListAdapter;

    private ListView mListView;

    String[] mCategories = new String[]{
            "Bookmarked", "All News", "Top", "National",
            "International", "Politics", "Business", "Sports",
            "Entertainment", "Technology", "Travel", "Health",
            "Food", "Fashion", "Education", "Viral",
            "Daily Quote", "Daily Titbit", "Event of the day", "Horoscope",
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
                Category category = new Category();
                category.setCategoryName(mCategories[i]);
                category.setIcon(mIcons[i]);

                mCategoryList.add(category);
            }

            Category category = new Category();

            category.setCategoryName("+More");
            mCategoryList.add(category);

            //Open a new activity when a list item from android custom listview is clicked.
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (position == 7) {
                        mCategoryList.clear();


                        for (int i = 0; i < mCategories.length; i++) {
                            Category category = new Category();
                            category.setCategoryName(mCategories[i]);
                            category.setIcon(mIcons[i]);

                            mCategoryList.add(category);
                        }
                        mCategoryListAdapter.notifyDataSetChanged();
                    }

                }
            });

        }




        return v;
    }


}
