package com.scienstechnologies.newsfeed.Menu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.scienstechnologies.newsfeed.MainActivity;
import com.scienstechnologies.newsfeed.R;

import java.util.List;

/**
 * Created by vamsitallapudi on 07-Jan-16.
 */
public class SettingsListAdapter extends BaseAdapter{

    private Activity mActivity;
    private List<SettingsModel> mSettingsList;
    private LayoutInflater mInflater;


    public SettingsListAdapter(Activity mActivity, List<SettingsModel> mSettingsList){
        this.mActivity = mActivity;
        this.mSettingsList = mSettingsList;
    }

    @Override
    public int getCount() {
        return mSettingsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mSettingsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(mInflater == null){
            mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }if(convertView == null){
            convertView = mInflater.inflate(R.layout.list_item_settings,null);
        }

        ImageView ivSettingsIcon = (ImageView) convertView.findViewById(R.id.ivSettingsIcon);
        TextView tvSettingsName = (TextView) convertView.findViewById(R.id.tvSettingsName);
        Switch switch1 = (Switch) convertView.findViewById(R.id.switch1);

        SettingsModel settingsModel = mSettingsList.get(position);
        ivSettingsIcon.setImageResource(settingsModel.getIcon());
        tvSettingsName.setText(settingsModel.getSettingName());

        if(settingsModel.isSwitchPresent()){
            switch1.setVisibility(View.VISIBLE);

            switch1.setChecked(settingsModel.isSwitchValue());

            if (switch1 != null) {
                switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    }
                });
            }






        }else{
            switch1.setVisibility(View.GONE);
        }
        return convertView;
    }
}
