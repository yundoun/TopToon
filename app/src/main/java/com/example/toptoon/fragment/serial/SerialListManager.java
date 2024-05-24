package com.example.toptoon.fragment.serial;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.toptoon.R;

public class SerialListManager extends FragmentStateAdapter {
    private final String[] DAYS;

    public SerialListManager(@NonNull SerialFragment fragmentActivity) {
        super(fragmentActivity);
        DAYS = fragmentActivity.getResources().getStringArray(R.array.serial);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position < DAYS.length) {
            return DayFragment.newInstance(DAYS[position]);
        } else {
            throw new IllegalStateException("탭수 초과");
        }
    }

    @Override
    public int getItemCount() {
        return DAYS.length; // 탭의 총 개수
    }
}
