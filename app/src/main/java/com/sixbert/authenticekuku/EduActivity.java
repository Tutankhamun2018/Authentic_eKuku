package com.sixbert.authenticekuku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class EduActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationItemView;
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    MaterialButton statsButton, infrastrButton,healthButton, foodNutrButton,newsButton, postnewsButton;
    //private AdView adView;
    public Toolbar toolbar;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_edu);


        toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        //MobileAds.initialize(this);//the SDK can reference the appID declared in the AndroidManifest
        //adView = (AdView)findViewById(R.id.banner_adEdu);
        //AdRequest adRequest = new AdRequest.Builder().build();
        //adView.loadAd(adRequest);
        //Interstitial Adverts
        /*MobileAds.initialize(this, "");
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("");
        AdRequest request = new AdRequest.Builder().build();
        interstitialAd.loadAd(request);
        interstitialAd.setAdListener(new AdListener(){
            public void onAdLoaded(){
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
            }
        });*/

        statsButton = findViewById(R.id.materialButton);
        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), StatsActivity.class));
            }
        });

        infrastrButton = findViewById(R.id.materialButtonInfrastruc);
        infrastrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), InfrastructureActivity.class));
            }
        });

        healthButton = findViewById(R.id.materialButtonHealth);
        healthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HealthActivity.class));
            }
        });

        foodNutrButton = findViewById(R.id.materialButtonFoodNutri);
        foodNutrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FandNActivity.class));
            }
        });

        newsButton = findViewById(R.id.materialButtonNews);
        newsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), InNewsActivity.class));
            }
        });

        /*postnewsButton = findViewById(R.id.materialButtonPost);
        postnewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PostNewsActivity.class));
            }
        });*/

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                drawerLayout.closeDrawers();

                if(itemId ==R.id.nav_edu){
                    return true;
                } else if (itemId ==R.id.nav_sell){
                    startActivity(new Intent(EduActivity.this, SellActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                } else if (itemId ==R.id.nav_buy){
                    startActivity(new Intent(EduActivity.this, BuyActivity2.class));
                    overridePendingTransition(0,0);
                    return true;
                } else if (itemId ==R.id.nav_home){
                    startActivity(new Intent(EduActivity.this, MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                /*switch (item.getItemId()){
                    case R.id.nav_home:
                        return true;

                    case R.id.nav_sell:
                        startActivity(new Intent(MainActivity.this, SellActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_buy:
                        startActivity(new Intent(MainActivity.this, BuyActivity2.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_edu:
                        startActivity(new Intent(MainActivity.this, EduActivity.class));
                        overridePendingTransition(0,0);
                        return true;*///
                }

                return false;
            }
        });

        bottomNavigationItemView = findViewById(R.id.bottom_navigation);
        //set home selected
        bottomNavigationItemView.setSelectedItemId(R.id.edu_activity);//continue
        // implement item selected listener
        bottomNavigationItemView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected (@NonNull MenuItem itemBtm) {
                int itemIdBtm = itemBtm.getItemId();
                   if (itemIdBtm == R.id.edu_activity) {
                       return true;
                   } else if(itemIdBtm == R.id.sell_activity) {
                       startActivity(new Intent(getApplicationContext(), SellActivity.class));
                       overridePendingTransition(0, 0);
                       return true;
                   } else if(itemIdBtm == R.id.buy_activity) {
                       startActivity(new Intent(getApplicationContext(), BuyActivity2.class));
                       overridePendingTransition(0, 0);
                       return true;
                   } else if(itemIdBtm == R.id.home1) {
                       startActivity(new Intent(getApplicationContext(), MainActivity.class));
                       overridePendingTransition(0, 0);
                       return true;

                }
                return false;


            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem){
        if(actionBarDrawerToggle.onOptionsItemSelected(menuItem)){
            return true;
        }

        return super.onOptionsItemSelected(menuItem);
    }
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        //Intent i= new Intent(EduActivity.this,MainActivity.class);
        //startActivity(i);
        finish();
    }

}