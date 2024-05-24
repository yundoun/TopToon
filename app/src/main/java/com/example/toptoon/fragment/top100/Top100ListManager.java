package com.example.toptoon.fragment.top100;

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
        return Top100BaseFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
