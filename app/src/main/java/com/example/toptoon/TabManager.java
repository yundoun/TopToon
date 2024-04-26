package com.example.toptoon;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TabManager extends FragmentStateAdapter {
    public TabManager(@NonNull HomeFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new TabNewItem();
            case 2:
                return new TabSale();
            case 3:
                return new TabMine();
            default:
                return new TabRealTime(); // 기본값
        }
    }

    @Override
    public int getItemCount() {
        // 탭의 개수 반환
        return 4;
    }
}
