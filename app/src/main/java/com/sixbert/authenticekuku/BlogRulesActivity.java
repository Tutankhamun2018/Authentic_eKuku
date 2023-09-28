package com.sixbert.authenticekuku;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class BlogRulesActivity extends AppCompatActivity {

    WebView rulesWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_blog_rules);

        rulesWebView = findViewById(R.id.blogRulesWV);

        rulesWebView.setWebViewClient(new WebViewClient());
        rulesWebView.loadUrl("file:///android_asset/raw/guidelines_blog.html");


    }
}
