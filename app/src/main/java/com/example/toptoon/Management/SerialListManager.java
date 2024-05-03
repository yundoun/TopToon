package com.example.toptoon.Management;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.toptoon.Fragment.MainListFragment;
import com.example.toptoon.Fragment.SerialFragment;

public class SerialListManager extends FragmentStateAdapter {
    public SerialListManager(@NonNull SerialFragment fragmentActivity) {
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
            case 4:
                return new MainListFragment();
            case 5:
                return new MainListFragment();
            case 6:
                return new MainListFragment();
            case 7:
                return new MainListFragment();
            case 8:
                return new MainListFragment();
            default:
                return null; // 이 경우가 발생하지 않도록 주의
        }
    }

    @Override
    public int getItemCount() {
        return 8; // 탭의 총 개수
    }
}
