package com.sixbert.authenticekuku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_stats);
        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //checkConnectivity();


        ViewPagerStats viewPagerStats = new ViewPagerStats(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(viewPagerStats);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Siku");
                    break;

                case 1:
                    tab.setText("Wiki");
                    break;

                case 2:
                   tab.setText("Mwezi");
                 break;
                case 3:
                    tab.setText("Tarehe kama Leo");
                    break;


            }
        }).attach();
    }


   /* private void checkConnectivity() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.new.conn.CONNECTIVITY_CHANGE");

        registerReceiver(new ConnectionReceiver(), intentFilter);

        ConnectionReceiver.Listener = this::onNetworkChange;

        ConnectivityManager cmanager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cmanager.getActiveNetworkInfo();
        if (activeNetwork != null) {
            Toast.makeText(StatsActivity.this, "Imeunganiswa mtandaoni", Toast.LENGTH_SHORT).show();

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
    }*/




}