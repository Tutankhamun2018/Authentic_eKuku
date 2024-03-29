package com.sixbert.authenticekuku;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;



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

        }

        return createFragment(position);
    }


    public int getItemCount(){
        return 2;
    }
}


