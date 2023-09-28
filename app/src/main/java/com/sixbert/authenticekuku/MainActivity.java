package com.sixbert.authenticekuku;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Filtered Values";

    public Toolbar toolbar;
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    TextView txt_date;

    TextView txt_qty_local,txt_qty_layer,txt_qty_hybrid, txt_egg_local, txt_egg_layer, txt_egg_hybrid;
    BottomNavigationView bottomNavigationItemView;

    boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets the phone's status bar transparent ie clock, charge visible;
        Window win = getWindow();
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        navigationView.bringToFront();
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                drawerLayout.closeDrawers();

                if (itemId == R.id.nav_home) {
                    return true;
                } else if (itemId == R.id.nav_sell) {
                    startActivity(new Intent(MainActivity.this, SellActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.nav_buy) {
                    startActivity(new Intent(MainActivity.this, BuyActivity2.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.nav_edu) {
                    startActivity(new Intent(MainActivity.this, EduActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.nav_terms) {
                    startActivity(new Intent(MainActivity.this, TermsActivity.class));
                    overridePendingTransition(0, 0);
                    return true;


                } else if (itemId == R.id.nav_support) {
                    startActivity(new Intent(MainActivity.this, WhoWeAreActivity.class));
                    overridePendingTransition(0, 0);
                    return true;

                } else if (itemId == R.id.nav_communicate) {
                    startActivity(new Intent(MainActivity.this, TalkToUsActivity.class));
                    overridePendingTransition(0, 0);
                    return true;

                }

                return false;
            }

        });

        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        String date = day + "/" + (month + 1) + "/" + year;
        txt_date = findViewById(R.id.txt_date);
        txt_date.setText(date);

        bottomNavigationItemView = findViewById(R.id.bottom_navigation);

        //set home selected
        bottomNavigationItemView.setSelectedItemId(R.id.home1);//continue
        // implement item selected listener
        bottomNavigationItemView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem itemBtm) {
                int itemIdBtm = itemBtm.getItemId();
                if (itemIdBtm == R.id.home1) {
                    return true;
                } else if(itemIdBtm == R.id.sell_activity) {
                    Intent intentSA = new Intent(getApplicationContext(), SellActivity.class);
                    intentSA.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentSA);
                    return true;
                } else if(itemIdBtm == R.id.edu_activity) {
                    Intent intentEA = new Intent(getApplicationContext(), EduActivity.class);
                    intentEA.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentEA);
                    return true;
                } else if(itemIdBtm == R.id.buy_activity) {
                    Intent intentBA = new Intent(getApplicationContext(), BuyActivity2.class);
                    intentBA.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentBA);
                    return true;
                }
                return false;

            }
        });
        txt_qty_local = findViewById(R.id.itemQtylocalchicken);
        txt_qty_layer = findViewById(R.id.itemQtybroiler);
        txt_qty_hybrid = findViewById(R.id.itemQtyhybrid);
        txt_egg_local = findViewById(R.id.itemQtylocalegg);
        txt_egg_layer = findViewById(R.id.qtyLayerEgg);
        txt_egg_hybrid = findViewById(R.id.qtyHybridEgg);




        FirebaseFirestore mUserDatabase = FirebaseFirestore.getInstance();

            Date morrow = new Date();
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.setTime(morrow);
            calendar.add(java.util.Calendar.DATE, 1);
            morrow = calendar.getTime();

            //yesterday
            Date yesterday = new Date();
            java.util.Calendar calendaryesterday = java.util.Calendar.getInstance();
            calendaryesterday.setTime(yesterday);
            calendaryesterday.add(java.util.Calendar.DATE, -1);
            yesterday = calendaryesterday.getTime();

            Query query = mUserDatabase.collectionGroup("postId")
                    .whereEqualTo("typeOfItem", "Kuku Kienyeji")
                .whereGreaterThan("today", yesterday).whereLessThan("today", morrow);
        AggregateQuery countQuery = query.count();
        countQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
           @Override
          public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {


                 if (task.isSuccessful()) {
                     AggregateQuerySnapshot snapshot = task.getResult();
                   Log.d(TAG, "Kuku kienyeji Count : " + snapshot.getCount());//sum = sum + price;

                    txt_qty_local.setText(String.valueOf(snapshot.getCount()));


               } else {
                  Log.d(TAG, "Kuku Kienyeji Count failed: ", task.getException());
                }


           }
         });



            Query query_kisasa = mUserDatabase.collectionGroup("postId")
                    .whereEqualTo("typeOfItem", "Kuku Kisasa")
                    .whereGreaterThan("today", yesterday).whereLessThan("today", morrow);
            AggregateQuery countQueryKisasa = query_kisasa.count();
            countQueryKisasa.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {

                    if (task.isSuccessful()) {
                        AggregateQuerySnapshot snapshot = task.getResult();
                        Log.d(TAG, "Kuku kisasa Count : " + snapshot.getCount());//sum = sum + price;

                        txt_qty_layer.setText(String.valueOf(snapshot.getCount()));

                    } else {
                        Log.d(TAG, "Kuku Kisasa Count failed: ", task.getException());
                    }

                }
            });

            Query query_chotara = mUserDatabase.collectionGroup("postId")
                    .whereEqualTo("typeOfItem", "Kuku Chotara")
                    .whereGreaterThan("today", yesterday).whereLessThan("today", morrow);
            AggregateQuery countQueryChotara = query_chotara.count();
            countQueryChotara.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {

                    if (task.isSuccessful()) {
                        AggregateQuerySnapshot snapshot = task.getResult();
                        Log.d(TAG, "Kuku Chotara Count : " + snapshot.getCount());//sum = sum + price;

                        txt_qty_hybrid.setText(String.valueOf(snapshot.getCount()));

                    } else {
                        Log.d(TAG, "Kuku Chotara Count failed: ", task.getException());
                    }

                }
            });

            Query query_yai_local = mUserDatabase.collectionGroup("postId")
                    .whereEqualTo("typeOfItem", "Mayai Kienyeji (Trei)")
                    .whereGreaterThan("today", yesterday).whereLessThan("today", morrow);
            AggregateQuery countQueryYaiLocal = query_yai_local.count();
            countQueryYaiLocal.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {

                    if (task.isSuccessful()) {
                        AggregateQuerySnapshot snapshot = task.getResult();
                        Log.d(TAG, "Mayai Kienyeji Count : " + snapshot.getCount());//sum = sum + price;

                        txt_egg_local.setText(String.valueOf(snapshot.getCount()));
                        //txt_price_local.setText(String.valueOf(sum));

                    } else {
                        Log.d(TAG, "Kuku Kisasa Count failed: ", task.getException());
                    }

                }
            });

            Query yai_kisasa = mUserDatabase.collectionGroup("postId")
                    .whereEqualTo("typeOfItem", "Mayai Kisasa (Trei)")
                    .whereGreaterThan("today", yesterday).whereLessThan("today", morrow);
            AggregateQuery countYaiKisasa = yai_kisasa.count();
            countYaiKisasa.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {

                    if (task.isSuccessful()) {
                        AggregateQuerySnapshot snapshot = task.getResult();
                        Log.d(TAG, "Yai kisasa Count : " + snapshot.getCount());//sum = sum + price;

                        txt_egg_layer.setText(String.valueOf(snapshot.getCount()));

                    } else {
                        Log.d(TAG, "Yai Kisasa Count failed: ", task.getException());
                    }

                }
            });

            Query query_yai_hybr = mUserDatabase.collectionGroup("postId")
                    .whereEqualTo("typeOfItem", "Mayai Chotara (Trei)")
                    .whereGreaterThan("today", yesterday).whereLessThan("today", morrow);
            AggregateQuery countYaiHybrid = query_yai_hybr.count();
            countYaiHybrid.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {

                    if (task.isSuccessful()) {
                        AggregateQuerySnapshot snapshot = task.getResult();
                        Log.d(TAG, "Yai Chotara Count : " + snapshot.getCount());//sum = sum + price;

                        txt_egg_hybrid.setText(String.valueOf(snapshot.getCount()));

                    } else {
                        Log.d(TAG, "Yai Chotara Count failed: ", task.getException());
                    }

                }
            });


    }


/*
@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
private void checkConnectivity() {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.new.conn.CONNECTIVITY_CHANGE");

    registerReceiver(new ConnectionReceiver(), intentFilter, RECEIVER_NOT_EXPORTED);

    ConnectionReceiver.Listener = this::onNetworkChange;

    ConnectivityManager cManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = cManager.getActiveNetworkInfo();
    boolean isConnectd = activeNetwork !=null && activeNetwork.isConnectedOrConnecting();
    //if (activeNetwork != null) {
        //Toast.makeText(MainActivity.this, "OK!", Toast.LENGTH_SHORT).show();
    showAlertDialog(isConnected);
    //} else {

    //}
}
        
    private void showAlertDialog(boolean isConnected) {
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
        showAlertDialog(isConnected);
    }*/



    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
   /* @Override
    protected void onPause() {
        super.onPause();
        // call method
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkConnectivity();
        }
    }*/

    public void onBackPressed(){

        new AlertDialog.Builder(this)
                .setTitle("Kufunga")
                .setMessage("Hakika unataka kufunga?")
                .setNegativeButton(R.string.nope, null)
                .setPositiveButton(R.string.yeah, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface arg0, int arg1){
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();

    }

}




