package com.sixbert.authenticekuku;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class WhoWeAreActivity extends AppCompatActivity {


    String twitter_user_name = "https://twitter.com/Dr_Sixbert";
    WebView mission;
    ImageView eKukuImage, youtube, twitter, fbook, instagram;

    @SuppressLint("SetJavaScriptEnabled")




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_whoweare);


        mission = findViewById(R.id.mission);
        eKukuImage = findViewById(R.id.ekukuImage);
        youtube = findViewById(R.id.youtube);
        twitter = findViewById(R.id.tweetter);
        fbook = findViewById(R.id.fbook);
        instagram = findViewById(R.id.instagram);

        mission.setWebViewClient(new WebViewClient());
        mission.loadUrl("file:///android_asset/raw/whoweare.html");
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCALr7SVQPfcPzT_EvAmDYew")));

            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             try { startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                     "https://twitter.com/Dr_Sixbert")));
            }catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                        "https://twitter.com/Dr_Sixbert")));
            }

            }
        });

        fbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }


}