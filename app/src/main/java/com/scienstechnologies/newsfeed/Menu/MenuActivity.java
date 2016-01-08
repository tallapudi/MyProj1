package com.scienstechnologies.newsfeed.Menu;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.scienstechnologies.newsfeed.Menu.slidingtab.SlidingTabLayout;
import com.scienstechnologies.newsfeed.R;

public class MenuActivity extends FragmentActivity {


    ViewPager pager;
    MenuTabAdapter adapter;
    SlidingTabLayout mSlidingTabLayout;
    CharSequence Titles[] = {"Menu", "Settings"};
    int NumOfTabs= 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        adapter = new MenuTabAdapter(getSupportFragmentManager(),Titles,NumOfTabs);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
        mSlidingTabLayout.setDistributeEvenly(true);

        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position){
                return getResources().getColor(R.color.white);
            }
        });

        mSlidingTabLayout.setViewPager(pager);
    }
}
