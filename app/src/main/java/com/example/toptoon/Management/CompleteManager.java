package com.example.toptoon.Management;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.toptoon.Fragment.CompleteFragment;
import com.example.toptoon.Fragment.MainListFragment;

public class CompleteManager extends FragmentStateAdapter {
    public CompleteManager(@NonNull CompleteFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
            case 1:
            case 2:
            case 3:
                return new MainListFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
