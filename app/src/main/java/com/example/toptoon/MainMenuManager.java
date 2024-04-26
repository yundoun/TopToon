package com.example.toptoon;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainMenuManager extends FragmentStateAdapter {
    public MainMenuManager(@NonNull SerialFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new SerialFragment();
            case 1:
                return new SerialFragment();
            case 2:
                return new SerialFragment();
            default:
                return null; // 이 경우가 발생하지 않도록 주의
        }
    }

    @Override
    public int getItemCount() {
        return 8; // 탭의 총 개수
    }
}
