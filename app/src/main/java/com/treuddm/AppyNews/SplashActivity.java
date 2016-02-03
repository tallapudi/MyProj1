package com.treuddm.AppyNews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.treuddm.AppyNews.Intro.IntroActivity;

public class SplashActivity extends AppCompatActivity {

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



        setContentView(R.layout.activity_splash);


        Thread timerThread = new Thread(){
            public void run(){
                try {
                    sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {


                    SharedPreferences prefs = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                    boolean haveWeShownPreferences = prefs.getBoolean("HaveShownPrefs", false);

                    if (!haveWeShownPreferences) {
                        // launch the preferences activity


                        Intent i = new Intent(SplashActivity.this,IntroActivity.class);
                        startActivity(i);

                    } else {
                        // we have already shown the preferences activity before
                        Intent i = new Intent(SplashActivity.this,MainActivity.class);
                        startActivity(i);
                    }



                }
            }
        };
        timerThread.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
