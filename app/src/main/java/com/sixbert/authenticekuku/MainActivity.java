package com.sixbert.authenticekuku;


import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryPurchasesParams;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Filtered Values";

    public Toolbar toolbar;
    private BillingClient billingClient;
    boolean isPremium = false;
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    String msg = "Done";

    ImageView itemImage, imagebroiler,imagehybrid,imagelocalEgg,imagelayerEgg, imagehybridEgg;

    TextView txt_date;

    TextView txt_qty_local,txt_qty_layer,txt_qty_hybrid, txt_egg_local, txt_egg_layer, txt_egg_hybrid;
    BottomNavigationView bottomNavigationItemView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets the phone's status bar transparent ie clock, charge visible;
        Window win = getWindow();
        overridePendingTransition(0,0);
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_main);

       billingClient = BillingClient.newBuilder(this)
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();
        queryPurchase();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        itemImage = findViewById(R.id.itemImage);
        imagebroiler = findViewById(R.id.imagebroiler);
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
                    startActivity(new Intent(MainActivity.this, BuyActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.nav_edu) {
                    startActivity(new Intent(MainActivity.this, EduActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }else if (itemId == R.id.termsandconditions) {
                    startActivity(new Intent(MainActivity.this, TCActivity.class));
                    overridePendingTransition(0, 0);
                    return true;


                } else if (itemId == R.id.nav_terms) {
                    String url = "https://dasgtech.com/ekuku-app-tz-privacy-policy/";
                        Uri uri = Uri.parse(url);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
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

                else if (itemId == R.id.nav_account_settings) {
                    startActivity(new Intent(MainActivity.this, AccountMenu.class));
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
                    startActivity(new Intent(getApplicationContext(), SellActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    return true;
                } else if(itemIdBtm == R.id.edu_activity) {
                    startActivity(new Intent(getApplicationContext(), EduActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    return true;
                } else if(itemIdBtm == R.id.buy_activity) {
                    startActivity(new Intent(getApplicationContext(), BuyActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
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
                   //Log.d(TAG, "Kuku kienyeji Count : " + snapshot.getCount());//sum = sum + price;

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
                        //Log.d(TAG, "Mayai Kienyeji Count : " + snapshot.getCount());//sum = sum + price;

                        txt_egg_local.setText(String.valueOf(snapshot.getCount()));

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


        FirebaseMessaging.getInstance().subscribeToTopic("News")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //String msg = "Done";

                        if(!task.isSuccessful()){
                            msg = "Failed";
                        }
                    }

                });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }




private final PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {

    }
};

private void queryPurchase(){
    billingClient.startConnection(new BillingClientStateListener() {
        @Override
        public void onBillingServiceDisconnected() {
            //queryPurchase();

        }

        @Override
        public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
            if(billingResult.getResponseCode()== BillingClient.BillingResponseCode.OK){
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(()->{
                    try {
                        billingClient.queryPurchasesAsync(
                                QueryPurchasesParams.newBuilder()
                                        .setProductType(BillingClient.ProductType.SUBS)
                                        .build(), (billingResult1, purchaseList)->{
                            for (Purchase purchase : purchaseList) {
                                if (purchase != null && purchase.isAcknowledged()) {
                                    isPremium = true;
                                }
                            }
                        }
                        );
                    } catch (Exception e){
                        isPremium = false;

                    }
                    runOnUiThread(()->{
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException interruptedException){
                            interruptedException.printStackTrace();
                        }
                        if (isPremium){
                            ConnectionClass.premium = true;
                            ConnectionClass.locked = false;

                        } else {
                            ConnectionClass.premium = false;
                            startActivity( new Intent(MainActivity.this, UnsubscribedMainActivity.class));
                            finish();
                        }
                    });
                });
            }

        }
    });


}
/*@Override
public void onConfigurationChanged(@NonNull Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    Intent intent = getIntent();
    //finish();
    startActivity(intent);
}*/
    

   /*getonBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
        @Override
        public void handleonBackPressed() {
            finish();
        }
    });*/

}




