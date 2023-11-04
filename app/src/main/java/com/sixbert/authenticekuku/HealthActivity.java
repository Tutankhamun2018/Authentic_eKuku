package com.sixbert.authenticekuku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HealthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        overridePendingTransition(0,0);
        setContentView(R.layout.activity_health);

        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPagerHealth viewPagerHealth = new ViewPagerHealth(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(viewPagerHealth);

        //Attach the View pager to the TabLayout
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText(getResources().getString(R.string.new_castle));
                        break;
                    case 1:
                        tab.setText(getResources().getString(R.string.gumboro));
                        break;

                    case 2:
                        tab.setText(getResources().getString(R.string.coryza));
                        break;
                    case 3:
                        tab.setText(getResources().getString(R.string.others));
                        break;


                }
            }


        }).attach();
    }
}