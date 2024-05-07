package com.example.toptoon.Management;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.toptoon.Fragment.MainListFragment;
import com.example.toptoon.Fragment.SerialFragment;
import com.example.toptoon.Serial.MondayFragment;
import com.example.toptoon.Serial.TuesdayFragment;
import com.example.toptoon.Serial.TursdayFragment;
import com.example.toptoon.Serial.WednesdayFragment;

public class SerialListManager extends FragmentStateAdapter {
    public SerialListManager(@NonNull SerialFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MondayFragment();
            case 1:
                return new TuesdayFragment();
            case 2:
                return new WednesdayFragment();
            case 3:
                return new TursdayFragment();
            case 4:
            case 5:
            case 6:
            case 7:
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
