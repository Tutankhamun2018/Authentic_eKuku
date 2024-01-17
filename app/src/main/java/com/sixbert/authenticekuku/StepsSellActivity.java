package com.sixbert.authenticekuku;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class StepsSellActivity extends AppCompatActivity {

   WebView sellStepswebView;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Window win = getWindow();
            win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            win.setStatusBarColor(Color.TRANSPARENT);
            setContentView(R.layout.activity_steps_sell);

            sellStepswebView = findViewById(R.id.stepsSellWV);

            sellStepswebView.setWebViewClient(new WebViewClient());
            sellStepswebView.loadUrl("file:///android_asset/raw/sell_steps.html");

        }
    }