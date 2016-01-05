package com.scienstechnologies.newsfeed;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ShareActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rel1);
        ImageView ivFacebook = (ImageView) findViewById(R.id.ivFacebook);
        ImageView ivTwitter = (ImageView) findViewById(R.id.ivTwitter);
        ImageView ivGooglePlus = (ImageView) findViewById(R.id.ivGooglePlus);
        ImageView ivLinkedin = (ImageView) findViewById(R.id.ivLinkedin);
        ImageView ivWhatsapp = (ImageView) findViewById(R.id.ivWhatsapp);
        ImageView ivEmail = (ImageView) findViewById(R.id.ivEmail);
        ImageView ivBookmark = (ImageView) findViewById(R.id.ivBookmark);
        ImageView ivCopy = (ImageView) findViewById(R.id.ivCopy);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        ivFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }
}
