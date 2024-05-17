package com.example.toptoon.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.toptoon.R;
import com.example.toptoon.Management.SerialListManager;
import com.example.toptoon.databinding.FragmentSerialBinding;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Calendar;

public class SerialFragment extends Fragment {

    FragmentSerialBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSerialBinding.inflate(inflater, container, false);
        initializeComponents();
        return binding.getRoot();
    }

    private void initializeComponents() {
        binding.vpSerial.setAdapter(new SerialListManager(this));
        new TabLayoutMediator(binding.tlSerial, binding.vpSerial, this::setUpTabTitles).attach();

        binding.vpSerial.setOffscreenPageLimit(8);

        addBadgesToTabs();
    }


    private void setUpTabTitles(TabLayout.Tab tab, int position) {
        String[] tabTitles = getResources().getStringArray(R.array.serial);
        if (position < tabTitles.length) {
            tab.setText(tabTitles[position]);
        }
    }

    private void addBadgesToTabs() {
        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        // 요일에 맞는 탭 인덱스를 맞추기 위한 배열
        int[] dayToTabIndex = {6, 0, 1, 2, 3, 4, 5}; // Calendar의 요일을 탭 인덱스로 매핑
        int tabIndex = dayToTabIndex[dayOfWeek - 1];

        if (tabIndex >= 0 && tabIndex < 7) { // Exclude the "REMAKE" tab
            TabLayout.Tab tab = binding.tlSerial.getTabAt(tabIndex);
            if (tab != null) {
                BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                badgeDrawable.setVisible(true);
                badgeDrawable.setBackgroundColor(getResources().getColor(R.color.red, null)); // Set the badge color to red
                badgeDrawable.setBadgeGravity(BadgeDrawable.TOP_END); // Position the badge at the top end
            }
        }
    }
}
