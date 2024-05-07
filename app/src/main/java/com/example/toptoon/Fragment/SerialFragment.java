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
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

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
//        binding.vpSerial.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                adjustViewPagerHeight(position);
//            }
//        });
    }

//    private void adjustViewPagerHeight(int position) {
//        binding.vpSerial.post(() -> {
//            RecyclerView recyclerView = (RecyclerView) binding.vpSerial.getChildAt(0);
//            if (recyclerView != null) {
//                recyclerView.measure(
//                        View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), View.MeasureSpec.EXACTLY),
//                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//
//                ViewGroup.LayoutParams params = binding.vpSerial.getLayoutParams();
//                params.height = recyclerView.getMeasuredHeight();
//                binding.vpSerial.setLayoutParams(params);
//            }
//        });
//    }

    private void setUpTabTitles(TabLayout.Tab tab, int position) {
        String[] tabTitles = getResources().getStringArray(R.array.serial);
        if (position < tabTitles.length) {
            tab.setText(tabTitles[position]);
        }
    }
}
