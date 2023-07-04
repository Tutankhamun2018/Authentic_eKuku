package com.sixbert.authenticekuku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HealthActivity extends AppCompatActivity {
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //logout = findViewById(R.id.btnLogout);

        //Attach the ViewPagerAdapter to the ViewPager

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
                        tab.setText(getResources().getString(R.string.fowltyphoid));
                        break;

                    case 3:
                        tab.setText(getResources().getString(R.string.coryza));
                        break;
                    case 4:
                        tab.setText(getResources().getString(R.string.others));
                        break;


                }
            }

            // public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {


        }).attach();
    }
}