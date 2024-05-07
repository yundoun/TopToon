package com.example.toptoon.Management;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.toptoon.Serial.DayFragment;
import com.example.toptoon.Fragment.SerialFragment;

public class SerialListManager extends FragmentStateAdapter {
    private static final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday", "Remake"};

    public SerialListManager(@NonNull SerialFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position < DAYS.length) {
            return DayFragment.newInstance(DAYS[position]);
        } else {
            throw new IllegalStateException("Position exceeds number of tabs available.");
        }
    }

    @Override
    public int getItemCount() {
        return DAYS.length; // 탭의 총 개수
    }
}
