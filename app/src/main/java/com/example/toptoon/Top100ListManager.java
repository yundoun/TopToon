package com.example.toptoon;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class Top100ListManager extends FragmentStateAdapter {
    public Top100ListManager(@NonNull Top100Fragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MainListFragment();
            case 1:
                return new MainListFragment();
            case 2:
                return new MainListFragment();
            case 3:
                return new MainListFragment();
            default:
                return null; // 이 경우가 발생하지 않도록 주의
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
