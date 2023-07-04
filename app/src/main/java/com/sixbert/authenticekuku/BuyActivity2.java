package com.sixbert.authenticekuku;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
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
import com.google.firebase.firestore.Query;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BuyActivity2 extends AppCompatActivity {
    private final List<BuyItems> buyItem = new ArrayList<>();
    static final String TAG = "SearchBox";
    public Toolbar toolbar;
    //BuyItemAdapter adapter;
    private SearchView searchView;
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    //private InterstitialAd interstitialAd;


    TabLayout tabLayout;

    NavigationBarView bottomNavigationItemView;
    //RecyclerView mRecyclerView;
    //Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy2);
        toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(null);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        //searchView =findViewById(R.id.search_fragment);

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
                    startActivity(new Intent(getApplicationContext(), SellActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if(itemIdBtm == R.id.edu_activity) {
                    startActivity(new Intent(getApplicationContext(), EduActivity.class));
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

        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //logout = findViewById(R.id.btnLogout);

        //Attach the ViewPagerAdapter to the ViewPager

        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(mViewPagerAdapter);


        //Attach the View pager to the TabLayout

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



            // public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {


        }).attach();





    }

    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        //Intent i= new Intent(BuyActivity2.this,MainActivity.class);
        //startActivity(i);
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

   /* public  boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filtermenu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();


        return true;
    }*/



}
