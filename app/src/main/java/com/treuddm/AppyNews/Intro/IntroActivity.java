package com.treuddm.AppyNews.Intro;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.treuddm.AppyNews.MainActivity;
import com.treuddm.AppyNews.R;

public class IntroActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
// Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }


        setContentView(R.layout.activity_intro);



        SharedPreferences prefs = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putBoolean("HaveShownPrefs", true);
        ed.commit();





        mViewPager = (ViewPager) findViewById(R.id.activity_intro_view_pager);
        Button bSkip = (Button) findViewById(R.id.bSkip);
        final Button bNext = (Button) findViewById(R.id.bNext);
        bSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();

        final MyAdapter myAdapter = new MyAdapter(fragmentManager);
        mViewPager.setAdapter(myAdapter);

        final DetailOnPageChangeListener mDetailOnPageChangeListener = new DetailOnPageChangeListener();
//        mViewPager.setOnPageChangeListener(mDetailOnPageChangeListener);
//
//        if (mDetailOnPageChangeListener.getCurrentPage()+1 == 4) {
//            bNext.setText("Finish");
//            bNext.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent i = new Intent(IntroActivity.this, MainActivity.class);
//                    startActivity(i);
//                }
//            });
//        }


        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {

            }
            public void onPageScrolled(final int position, float positionOffset, int positionOffsetPixels) {

                if(position ==3){
                    bNext.setText("Finish");
                    bNext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(IntroActivity.this, MainActivity.class);
                            startActivity(i);
                        }
                    });
                }
                else{
                    bNext.setText("Next");
                    bNext.setOnClickListener(new View.OnClickListener() {
                        int pos = position;
                        @Override
                        public void onClick(View v) {
                            mViewPager.setCurrentItem(pos+1);
                        }
                    });
                }

            }

            public void onPageSelected(int position) {
                // Check if this is the page you want.
            }
        });

//        bNext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                mViewPager.setCurrentItem(mDetailOnPageChangeListener.getCurrentPage() + 1);
//
//                if (mDetailOnPageChangeListener.getCurrentPage() + 1 == 4) {
//                    bNext.setText("Finish");
//                    bNext.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent i = new Intent(IntroActivity.this, MainActivity.class);
//                            startActivity(i);
//                        }
//                    });
//                }
//                //mDetailOnPageChangeListener.setCurrentPage(mDetailOnPageChangeListener.getCurrentPage()+1);
//                myAdapter.notifyDataSetChanged();
//            }
//        });


    }

    public class DetailOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {

        private int currentPage;

        @Override
        public void onPageSelected(int position) {
            currentPage = position;
        }

        public final int getCurrentPage() {
//
//            if(currentPage ==4){
//                Button bNext = (Button) findViewById(R.id.bNext);
//                bNext.setText("Finish");
//            }
            return currentPage;
        }


        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }
    }

    public static class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            return super.instantiateItem(container, position);
        }


        @Override
        public Fragment getItem(int position) {
            return IntroFragment.newInstance(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public int getCount() {
            return 4;
        }
    }


}
