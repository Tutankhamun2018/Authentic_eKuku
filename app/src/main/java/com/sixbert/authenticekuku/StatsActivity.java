package com.sixbert.authenticekuku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

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
}