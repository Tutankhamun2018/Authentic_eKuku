package com.sixbert.authenticekuku;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Date;

public class UnsubscribedMainActivity extends AppCompatActivity {


        private static final String TAG = "Filtered Values";

        public Toolbar toolbar;
        public DrawerLayout drawerLayout;
        public NavigationView navigationView;

    ImageView itemImage, imagebroiler,imagehybrid,imagelocalEgg,imagelayerEgg, imagehybridEgg;

        TextView txt_date;

        TextView txt_qty_local,txt_qty_layer,txt_qty_hybrid, txt_egg_local, txt_egg_layer, txt_egg_hybrid;
        BottomNavigationView bottomNavigationItemView;

        boolean isConnected;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //sets the phone's status bar transparent ie clock, charge visible;
            Window win = getWindow();
            overridePendingTransition(0,0);
            win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            win.setStatusBarColor(Color.TRANSPARENT);

            setContentView(R.layout.activity_main);

            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            drawerLayout = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.nav_view);
            itemImage = findViewById(R.id.itemImage);
            imagebroiler=findViewById(R.id.imagebroiler);
            imagehybrid = findViewById(R.id.imagehybrid);
            imagelocalEgg = findViewById(R.id.imagelocalEgg);
            imagelayerEgg = findViewById(R.id.imagelayerEgg);
            imagehybridEgg = findViewById(R.id.imagehybridEgg);


            itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder photoBuilder = new AlertDialog.Builder(view.getContext());
                    View mView = getLayoutInflater().inflate(R.layout.imagezoom, null);
                    PhotoView photoView = mView.findViewById(R.id.chrisbanesImageView);
                    photoView.setImageResource(R.drawable.localchicken);
                    photoBuilder.setView(mView);
                    AlertDialog mDialog = photoBuilder.create();
                    mDialog.show();
                }
            });

            imagelocalEgg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder photoBuilder = new AlertDialog.Builder(view.getContext());
                    View mView = getLayoutInflater().inflate(R.layout.imagezoom, null);
                    PhotoView photoView = mView.findViewById(R.id.chrisbanesImageView);
                    photoView.setImageResource(R.drawable.localeggs);
                    photoBuilder.setView(mView);
                    AlertDialog mDialog = photoBuilder.create();
                    mDialog.show();
                }
            });
            imagebroiler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder photoBuilder = new AlertDialog.Builder(view.getContext());
                    View mView = getLayoutInflater().inflate(R.layout.imagezoom, null);
                    PhotoView photoView = mView.findViewById(R.id.chrisbanesImageView);
                    photoView.setImageResource(R.drawable.broilerchicken);
                    photoBuilder.setView(mView);
                    AlertDialog mDialog = photoBuilder.create();
                    mDialog.show();
                }
            });
            imagehybrid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder photoBuilder = new AlertDialog.Builder(view.getContext());
                    View mView = getLayoutInflater().inflate(R.layout.imagezoom, null);
                    PhotoView photoView = mView.findViewById(R.id.chrisbanesImageView);
                    photoView.setImageResource(R.drawable.kuroilechicken);
                    photoBuilder.setView(mView);
                    AlertDialog mDialog = photoBuilder.create();
                    mDialog.show();
                }
            });
            imagelayerEgg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder photoBuilder = new AlertDialog.Builder(view.getContext());
                    View mView = getLayoutInflater().inflate(R.layout.imagezoom, null);
                    PhotoView photoView = mView.findViewById(R.id.chrisbanesImageView);
                    photoView.setImageResource(R.drawable.layerseggs);
                    photoBuilder.setView(mView);
                    AlertDialog mDialog = photoBuilder.create();
                    mDialog.show();
                }
            });
            imagehybridEgg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder photoBuilder = new AlertDialog.Builder(view.getContext());
                    View mView = getLayoutInflater().inflate(R.layout.imagezoom, null);
                    PhotoView photoView = mView.findViewById(R.id.chrisbanesImageView);
                    photoView.setImageResource(R.drawable.kuroileregg);
                    photoBuilder.setView(mView);
                    AlertDialog mDialog = photoBuilder.create();
                    mDialog.show();
                }
            });



            Calendar cal = Calendar.getInstance();
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);
            String date = day + "/" + (month + 1) + "/" + year;
            txt_date = findViewById(R.id.txt_date);
            txt_date.setText(date);

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



    @Override
        public void onConfigurationChanged(@NonNull Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
            Intent intent = getIntent();
            //finish();
            startActivity(intent);
        }

    public void onBackPressed() {

            super.onBackPressed();
            new AlertDialog.Builder(this)
                    .setTitle("Kufunga")
                    .setMessage("Hakika unataka kufunga?")
                    .setNegativeButton(R.string.nope, null)
                    .setPositiveButton(R.string.yeah, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            com.sixbert.authenticekuku.UnsubscribedMainActivity.super.onBackPressed();
                        }
                    }).create().show();

        }

    }




