package com.example.toptoon.fragment.tap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.toptoon.fragment.HomeFragment;

public class TabManager extends FragmentStateAdapter {
    public TabManager(@NonNull HomeFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {

            case 0:
            default:
                return new TabRealTime(); // 기본값
            case 1:
                return new TabNewItem();
            case 2:
                return new TabSale();
            case 3:
                return new TabMine();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
