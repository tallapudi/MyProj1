package com.treuddm.AppyNews;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.treuddm.AppyNews.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShareActivity extends Activity {

    private static final String TAG = ShareActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rel1);
        ImageView ivFacebook = (ImageView) findViewById(R.id.ivFacebook);
        ImageView ivTwitter = (ImageView) findViewById(R.id.ivTwitter);
        ImageView ivWhatsapp = (ImageView) findViewById(R.id.ivWhatsapp);
        ImageView ivBookmark = (ImageView) findViewById(R.id.ivBookmark);
        ImageView ivCopy = (ImageView) findViewById(R.id.ivCopy);
        ImageView ivClose = (ImageView) findViewById(R.id.ivClose);


        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ivFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share("com.facebook");
            }
        });


        ivTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share("com.twitter");

            }
        });
        ivWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                share("com.whatsapp");
            }
        });


        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.d(TAG, "UTF-8 should always be supported", e);
            throw new RuntimeException("URLEncoder.encode() failed for " + s);
        }
    }

    private File takeScreenshot() {

        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {

            // create bitmap screen capture
            View v1 = this.getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);
            // image naming and path  to include sd card  appending name you choose for file

            String pathToImage = Environment.getExternalStorageDirectory().getPath() + "/" + now + ".jpg";
            File imageFile = new File(pathToImage);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            Toast.makeText(this, "Screenshot Saved", Toast.LENGTH_LONG).show();

            return imageFile;

        } catch (Throwable e) {
            // Several error may come out with file handling or OOM


            Toast.makeText(this, "Error saving image! Please check if SDCard is properly inserted", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        }


    }


    private void share(String nameApp) {
        List<Intent> targetedShareIntents = new ArrayList<Intent>();
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("image/jpeg");
        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(share, 0);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                Intent targetedShare = new Intent(android.content.Intent.ACTION_SEND);
                targetedShare.setType("image/jpeg"); // put here your mime type


                try {
                    if (info.activityInfo.packageName.toLowerCase().contains(nameApp) ||
                            info.activityInfo.name.toLowerCase().contains(nameApp)) {
                        targetedShare.putExtra(Intent.EXTRA_TEXT, "My body of post/email");
                        Intent i = getIntent();
                        Uri myScreenshotUri = i.getParcelableExtra("screenshot");
                        targetedShare.putExtra(Intent.EXTRA_STREAM, myScreenshotUri);

                        targetedShare.setPackage(info.activityInfo.packageName);
                        targetedShareIntents.add(targetedShare);
                    }


                    Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Select app to share");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
                    startActivity(chooserIntent);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "App not found on device!", Toast.LENGTH_LONG).show();

                }
            }
        }
    }
}
