package com.scienstechnologies.newsfeed.Menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.scienstechnologies.newsfeed.Menu.SettingsModel;
import com.scienstechnologies.newsfeed.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vamsitallapudi on 07-Jan-16.
 */
public class SettingsFragment extends Fragment {

    private List<SettingsModel> mSettingsList = new ArrayList<>();
    private SettingsListAdapter mSettingsListAdapter;

    private ListView mListView;

    String[] mSettings = new String[]{
        "Notifications","Night Mode","Rate This App","Like This App"
    };

    int[] mIcons = new int[]{
            R.drawable.notification_icon,R.drawable.night_mode_icon,
            R.drawable.rate_app_icon,R.drawable.like_icon
    };

    public Boolean[] mSwitchPresent = new Boolean[]{
            true, true, false,false
    };
    public Boolean[] mSwitchValue = new Boolean[]{
            true,false,true,false
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tab_menu,container,false);
        mListView = (ListView) v.findViewById(R.id.list);

        for(int i= 0; i<mSettings.length;i++){


            SettingsModel settingsModel = new SettingsModel();
            settingsModel.setSettingName(mSettings[i]);
            settingsModel.setIcon(mIcons[i]);
            settingsModel.setSwitchPresent(mSwitchPresent[i]);//true
            settingsModel.setSwitchValue(mSwitchValue[i]);
            mSettingsList.add(settingsModel);

        }
        mSettingsListAdapter = new SettingsListAdapter(getActivity(),mSettingsList);
        mSettingsListAdapter.notifyDataSetChanged();
        mListView.setAdapter(mSettingsListAdapter);


        return v;
    }
}
