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

public class InfrastructureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window win = getWindow();
        win.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        win.setStatusBarColor(Color.TRANSPARENT);
        overridePendingTransition(0,0);
        setContentView(R.layout.activity_infrastrucure);

        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //logout = findViewById(R.id.btnLogout);

        //Attach the ViewPagerAdapter to the ViewPager

        ViewPagerAdapterFrastructure frastructurePagerAdapter = new ViewPagerAdapterFrastructure(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(frastructurePagerAdapter);

        //Attach the View pager to the TabLayout
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText(getResources().getString(R.string.cages));
                        break;

                    case 1:
                        tab.setText(getResources().getString(R.string.sheds));
                        break;


                }
            }

            // public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {


        }).attach();
    }
}