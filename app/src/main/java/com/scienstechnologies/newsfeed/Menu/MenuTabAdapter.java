package com.scienstechnologies.newsfeed.Menu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.RelativeLayout;

/**
 * Created by vamsitallapudi on 05-Jan-16.
 */
public class MenuTabAdapter extends FragmentStatePagerAdapter {


    CharSequence Titles[];
    int NumOfTabs;


    public MenuTabAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabs) {
        super(fm);

        this.Titles = mTitles;
        this.NumOfTabs = mNumbOfTabs;

    }


    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return new MenuFragment();
        } else {
            return new SettingsFragment();
        }
    }

    @Override
    public int getCount() {
        return NumOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }


}
