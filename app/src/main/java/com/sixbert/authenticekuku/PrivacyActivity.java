package com.sixbert.authenticekuku;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class PrivacyActivity extends AppCompatActivity {

    WebView terms;
    public DrawerLayout drawerLayout;
    //public NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_terms);
        drawerLayout = findViewById(R.id.drawer_layout);
        //navigationView = findViewById(R.id.nav_view);

        terms = findViewById(R.id.tcWeb);

        //terms.getSettings().setJavaScriptEnabled(true);
        terms.setWebViewClient(new WebViewClient());
        terms.loadUrl("file:///android_asset/raw/privacypolicy.html");

    }

    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        Intent i= new Intent(PrivacyActivity.this,MainActivity.class);
        startActivity(i);

        finish();
    }


}