package com.scienstechnologies.newsfeed.Menu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.scienstechnologies.newsfeed.R;

/**
 * Created by vamsitallapudi on 08-Jan-16.
 */
public class MySettingsFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.list_item_my_settings,container,false);
        Switch sNotifications = (Switch) v.findViewById(R.id.sNotifications);
        Switch sNightMode = (Switch) v.findViewById(R.id.sNightMode);

        SharedPreferences sharedPrefs = getActivity().getSharedPreferences("NightMode", Context.MODE_PRIVATE);
        sNightMode.setChecked(sharedPrefs.getBoolean("nightmode", true));

        sNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        sNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SharedPreferences sharedPref = getActivity().getSharedPreferences("NightMode",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("nightmode", true);
                    editor.commit();
                }else {
                    SharedPreferences sharedPref = getActivity().getSharedPreferences("NightMode",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("nightmode", false);
                    editor.commit();
                }


            }
        });



        return v;
    }
}
