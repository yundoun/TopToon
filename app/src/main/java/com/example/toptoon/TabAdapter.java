package com.example.toptoon;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TabAdapter extends FragmentStateAdapter {
    public TabAdapter(@NonNull HomeFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // 여기서 position에 따라 다른 Fragment를 반환
        switch (position) {
            case 0:
                return new TabFragment1();
            case 1:
                return new TabFragment1();
            case 2:
                return new TabFragment1();
            case 3:
                return new TabFragment2();
            default:
                return new TabFragment1(); // 기본값
        }
    }

    @Override
    public int getItemCount() {
        // 탭의 개수 반환
        return 4;
    }
}
