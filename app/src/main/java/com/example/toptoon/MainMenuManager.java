package com.example.toptoon;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainMenuManager extends FragmentStateAdapter {
    public MainMenuManager(@NonNull SerialFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MondayFragment();
            case 1:
                return new MondayFragment();
            case 2:
                return new MondayFragment();
            case 3:
                return new MondayFragment();
            case 4:
                return new MondayFragment();
            case 5:
                return new MondayFragment();
            case 6:
                return new MondayFragment();
            case 7:
                return new MondayFragment();
            case 8:
                return new MondayFragment();
            default:
                return null; // 이 경우가 발생하지 않도록 주의
        }
    }

    @Override
    public int getItemCount() {
        return 8; // 탭의 총 개수
    }
}
