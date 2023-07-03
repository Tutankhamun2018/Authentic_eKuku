package com.sixbert.authenticekuku;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;



public class ViewPagerAdapter extends FragmentStateAdapter {
    //int mNumOfTabs;

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        //this.mNumOfTabs = mNumOfTabs;
    }@NonNull

    public Fragment createFragment (int position){
        switch (position){
            case 0:
                return new LocalChickenFragment();
            case 1:
                return new BroilerChickenFragment();
            case 2:
                return new HybridChickenFragment();
            case 3:
                return new LocalEggFragment();
            case 4:
                return new LayersEggFragment();
            case 5:
                return new ChotaraEggFragment();
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
        return 6;
    }
}
