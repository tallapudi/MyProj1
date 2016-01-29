package com.treuddm.AppyNews.Menu;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.treuddm.AppyNews.Menu.slidingtab.SlidingTabLayout;
import com.treuddm.AppyNews.R;

public class MenuActivity extends FragmentActivity {


    ViewPager pager;
    MenuTabAdapter adapter;
    RelativeLayout rlTotalContent;
    SlidingTabLayout mSlidingTabLayout;
    ImageView ivClose;
    CharSequence Titles[] = {"Menu", "Settings"};
    int NumOfTabs= 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        adapter = new MenuTabAdapter(getSupportFragmentManager(),Titles,NumOfTabs);

        pager = (ViewPager) findViewById(R.id.pager);
        rlTotalContent = (RelativeLayout) findViewById(R.id.rlTotalContent);
        pager.setAdapter(adapter);
        ivClose = (ImageView) findViewById(R.id.ivClose);
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
        mSlidingTabLayout.setDistributeEvenly(true);

        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });
        mSlidingTabLayout.setViewPager(pager);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        SharedPreferences sharedPrefs = getSharedPreferences("NightMode", Context.MODE_PRIVATE);
//        Boolean nightMode = sharedPrefs.getBoolean("nightmode", false);
//
//        if(nightMode){
//            rlTotalContent.setBackgroundColor(getResources().getColor(R.color.black));
//        }
//        else{
//            rlTotalContent.setBackgroundColor(getResources().getColor(R.color.translucent_color));
//        }

    }
}
