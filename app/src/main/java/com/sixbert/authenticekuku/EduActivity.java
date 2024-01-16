package com.sixbert.authenticekuku;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class EduActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationItemView;
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    MaterialButton statsButton;
    MaterialButton infrastrButton;
    MaterialButton healthButton;
    //ProgressBar progressBar;
    private int i;
    MaterialButton postnewsButton;
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
        toolbar.setNavigationIcon(R.drawable.back_arrow);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        //progressBar = findViewById(R.id.progressBar);



        //MobileAds.initialize(this);//the SDK can reference the appID declared in the AndroidManifest
        //adView = (AdView)findViewById(R.id.banner_adEdu);
        //AdRequest adRequest = new AdRequest.Builder().build();
        //adView.loadAd(adRequest);
        //Interstitial Adverts

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EduActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

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


        postnewsButton = findViewById(R.id.materialButtonPost);
        postnewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PostNewsActivity.class));


            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
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
                    startActivity(new Intent(EduActivity.this, BuyActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                } else if (itemId ==R.id.nav_home){
                    startActivity(new Intent(EduActivity.this, MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;
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
                       startActivity(new Intent(getApplicationContext(), SellActivity.class)
                               .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                       return true;
                   } else if(itemIdBtm == R.id.buy_activity) {
                       startActivity(new Intent(getApplicationContext(), BuyActivity.class)
                               .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                       return true;
                   } else if(itemIdBtm == R.id.home1) {
                       startActivity(new Intent(getApplicationContext(), MainActivity.class)
                               .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
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
        //finish();
        startActivity(intent);
    }


    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        Intent i= new Intent(EduActivity.this,MainActivity.class);
        startActivity(i);

        finish();
    }


    }