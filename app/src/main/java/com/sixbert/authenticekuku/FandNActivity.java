package com.sixbert.authenticekuku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FandNActivity extends AppCompatActivity {

    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fand_nactivity);

        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //logout = findViewById(R.id.btnLogout);

        //Attach the ViewPagerAdapter to the ViewPager

        ViewPagerFandN viewPagerFandN = new ViewPagerFandN(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(viewPagerFandN);

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
                        tab.setText(getResources().getString(R.string.layersChicken));
                        break;


                }
            }

            // public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {


        }).attach();
    }
}