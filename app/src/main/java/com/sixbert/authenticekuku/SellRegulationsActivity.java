package com.sixbert.authenticekuku;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class SellRegulationsActivity extends AppCompatActivity {
    WebView sellStepswebView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_sell_regulations);

        sellStepswebView = findViewById(R.id.blogRulesWV);

        sellStepswebView.setWebViewClient(new WebViewClient());
        sellStepswebView.loadUrl("file:///android_asset/raw/seller_regulations.html");


    }
}
