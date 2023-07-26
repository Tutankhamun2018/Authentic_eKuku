package com.sixbert.authenticekuku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;

public class ViewPagerAdapterFrastructure extends FragmentStateAdapter {




    public ViewPagerAdapterFrastructure(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        //this.mNumOfTabs = mNumOfTabs;
    }

    public ViewPagerAdapterFrastructure(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull

    public Fragment createFragment (int position){
        switch (position){
            case 0:
                return new VizimbaFragment();
            case 1:
                return new MabandaFragment();
            case 2:
                return new FeederDrinkerFragment();
            case 3:
                return new IncubatorFragment();
            //case 4:
            //    return new OtherFrastructureFragment();

        }

        return createFragment(position);
    }

    //Add text to the tabs

    /*public CharSequence getPageTitle(int position){
        switch (position) {
            case 0:
                return getResources().getText(R.string.tab_sell);
            case 1:
                return getResources().getText(R.string.tab_buy);
            case 2:
                return getResources().getText(R.string.tab_extras);
        }
        return null;
    }*/
    public int getItemCount(){
        return 4;
    }
}


