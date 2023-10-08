package com.sixbert.authenticekuku;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BuyActivity2 extends AppCompatActivity {
    private final List<BuyItems> buyItem = new ArrayList<>();
    static final String TAG = "SearchBox";
    public Toolbar toolbar;
    private SearchView searchView;
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    TabLayout tabLayout;
    NavigationBarView bottomNavigationItemView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        overridePendingTransition(0,0);
        setContentView(R.layout.activity_buy2);

        //checkConnectivity();


        toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                drawerLayout.closeDrawers();

                if(itemId ==R.id.nav_buy){
                    return true;
                } else if (itemId ==R.id.nav_sell){
                    startActivity(new Intent(getApplicationContext(), SellActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                } else if (itemId ==R.id.nav_home){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                } else if (itemId ==R.id.nav_edu){
                    startActivity(new Intent(getApplicationContext(), EduActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                }
                return false;
            }
        });



        FirebaseFirestore mUserDatabase = FirebaseFirestore.getInstance();

        CollectionReference ekukuRef = mUserDatabase
                .collection("eKuku");

        bottomNavigationItemView = findViewById(R.id.bottom_navigation);
        //set home selected
        bottomNavigationItemView.setSelectedItemId(R.id.buy_activity);//continue
        // implement item selected listener
        bottomNavigationItemView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected (@NonNull MenuItem itemBtm) {
                int itemIdBtm = itemBtm.getItemId();
                if (itemIdBtm == R.id.buy_activity) {
                    return true;
                } else if(itemIdBtm == R.id.sell_activity) {
                    startActivity(new Intent(getApplicationContext(), SellActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    return true;
                } else if(itemIdBtm == R.id.edu_activity) {
                    startActivity(new Intent(getApplicationContext(), EduActivity.class)
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

        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(mViewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText(getResources().getString(R.string.localchicken));
                        break;
                    case 1:
                        tab.setText(getResources().getString(R.string.broilerchicken));
                        break;
                    case 2:
                        tab.setText(getResources().getString(R.string.hybridchicken));
                        break;

                    case 3:
                        tab.setText(getResources().getString(R.string.egglocal));
                        break;

                    case 4:
                        tab.setText(getResources().getString(R.string.egglayers));
                        break;

                    case 5:
                        tab.setText(getResources().getString(R.string.egghybrid));
                        break;


                }
            }


        }).attach();

    }

    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        Intent i= new Intent(BuyActivity2.this,MainActivity.class);
        startActivity(i);

        finish();
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


   /* private void checkConnectivity() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.new.conn.CONNECTIVITY_CHANGE");

        registerReceiver(new ConnectionReceiver(), intentFilter);

        ConnectionReceiver.Listener = this::onNetworkChange;

        ConnectivityManager cmanager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cmanager.getActiveNetworkInfo();
        if (activeNetwork != null) {
            Toast.makeText(BuyActivity2.this, "OK!", Toast.LENGTH_SHORT).show();

        } else {
            showAlertDialog();
        }
    }

    private void showAlertDialog() {
        try {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Oops!");
            builder.setCancelable(true);
            builder.setMessage("Pole!.. Hujaunganishwa kwenye Intanet, angalia mtandao na ujaribu tena");
            builder.setNegativeButton("Funga", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();

        }catch (Exception e)

        {
            e.printStackTrace();
        }

    }
    public void onNetworkChange(boolean isConnected){
        showAlertDialog();
    }
    protected void onPause() {
        super.onPause();
        // call method
        checkConnectivity();
    }*/


}
