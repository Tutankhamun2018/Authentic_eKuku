package com.sixbert.authenticekuku;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerStats extends FragmentStateAdapter {

    public ViewPagerStats(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        //this.mNumOfTabs = mNumOfTabs;
    }

    public ViewPagerStats(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull

    public Fragment createFragment (int position){
        switch (position){
            case 0:
                return new DailyStatsFragment();
            case 1:
                return new WeeklyStatsFragment();
            case 2:
                return new MonthlyStatsFragment();
            case 3:
                return new HistoricalFragment();
                    }

        return createFragment(position);
    }


    public int getItemCount(){
        return 4;
    }
}


