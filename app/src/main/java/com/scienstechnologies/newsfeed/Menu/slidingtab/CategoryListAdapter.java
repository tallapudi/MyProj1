package com.scienstechnologies.newsfeed.Menu.slidingtab;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.scienstechnologies.newsfeed.Menu.Category;
import com.scienstechnologies.newsfeed.R;

import java.util.List;

/**
 * Created by vamsitallapudi on 05-Jan-16.
 */
public class CategoryListAdapter extends BaseAdapter{

    private Activity mActivity;
    private List<Category> categoryList;
    private LayoutInflater mInflater;


    public CategoryListAdapter(Activity mActivity, List<Category> categoryList){
        this.mActivity = mActivity;
        this.categoryList = categoryList;
    }


    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
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
        convertView = mInflater.inflate(R.layout.list_item, null);
    }
        ImageView ivCategoryIcon = (ImageView) convertView.findViewById(R.id.ivCategoryIcon);
        TextView tvCategoryName = (TextView) convertView.findViewById(R.id.tvCategoryName);

        Category category = categoryList.get(position);
        ivCategoryIcon.setImageResource(category.getIcon());
        tvCategoryName.setText(category.getCategoryName());
        return convertView;
    }
}
