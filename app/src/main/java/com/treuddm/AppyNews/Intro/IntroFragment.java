package com.treuddm.AppyNews.Intro;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.treuddm.AppyNews.R;

/**
 * Created by vamsitallapudi on 31-Jan-16.
 */
public class IntroFragment extends Fragment {

    int position;
    int introImages[] = {R.drawable.intro_1,R.drawable.intro_2,R.drawable.intro_3,R.drawable.intro_4};


    public static IntroFragment newInstance(int position){
        IntroFragment introFragment= new IntroFragment(position);
        return introFragment;
    }
    public IntroFragment(){

    }

    public IntroFragment(int position){
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.fragment_intro,container,false);

        LinearLayout llIntroFragment = (LinearLayout) v.findViewById(R.id.llIntroFragment);
        llIntroFragment.setBackgroundResource(introImages[position]);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
