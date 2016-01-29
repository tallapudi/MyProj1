package com.treuddm.AppyNews.Menu;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.treuddm.AppyNews.R;

import java.util.List;

/**
 * Created by vamsitallapudi on 05-Jan-16.
 */
public class CategoryListAdapter extends BaseAdapter{

    private Activity mActivity;
    private List<CategoryModel> mCategoryList;
    private LayoutInflater mInflater;

    public CategoryListAdapter(Activity mActivity, List<CategoryModel> mCategoryList){
        this.mActivity = mActivity;
        this.mCategoryList = mCategoryList;
    }


    @Override
    public int getCount() {
        return mCategoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCategoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(mInflater == null){
            mInflater = (LayoutInflater)mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }if(convertView == null){
        convertView = mInflater.inflate(R.layout.list_item_menu, null);
    }
        ImageView ivCategoryIcon = (ImageView) convertView.findViewById(R.id.ivCategoryIcon);
        TextView tvCategoryName = (TextView) convertView.findViewById(R.id.tvCategoryName);

        CategoryModel categoryModel = mCategoryList.get(position);
        ivCategoryIcon.setImageResource(categoryModel.getIcon());
        tvCategoryName.setText(categoryModel.getCategoryName());
        if(categoryModel.isSwitchPresent()==true){
            Switch switch1 = (Switch) convertView.findViewById(R.id.switch1);
            switch1.setChecked(categoryModel.isSwitchValue());
        }
        return convertView;
    }
}
