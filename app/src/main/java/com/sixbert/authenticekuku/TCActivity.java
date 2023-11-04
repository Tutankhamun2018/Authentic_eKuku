package com.sixbert.authenticekuku;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TCActivity extends AppCompatActivity {
    WebView tandc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_tcactivity);

        tandc = findViewById(R.id.tandcWeb);

        //terms.getSettings().setJavaScriptEnabled(true);
        tandc.setWebViewClient(new WebViewClient());
        tandc.loadUrl("file:///android_asset/raw/terms_and_conditions_sw.html");

    }

}
