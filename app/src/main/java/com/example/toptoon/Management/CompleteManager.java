package com.example.toptoon.Management;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.toptoon.Fragment.CompleteFragment;
import com.example.toptoon.Fragment.MainListFragment;
import com.example.toptoon.complete.BaseFragment;

public class CompleteManager extends FragmentStateAdapter {
    public CompleteManager(@NonNull CompleteFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return BaseFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
