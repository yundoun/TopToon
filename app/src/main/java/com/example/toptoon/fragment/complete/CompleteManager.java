package com.example.toptoon.fragment.complete;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CompleteManager extends FragmentStateAdapter {
    public CompleteManager(@NonNull CompleteFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return BaseCompleteFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
