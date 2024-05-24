package com.example.toptoon.fragment.top100;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.toptoon.R;
import com.example.toptoon.databinding.FragmentTop100Binding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class Top100Fragment extends Fragment {
    FragmentTop100Binding binding;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTop100Binding.inflate(inflater, container, false);
        initializeComponents();
        return binding.getRoot();
    }

    private void initializeComponents() {
        binding.vpTOP100.setAdapter(new Top100ListManager(this));
        new TabLayoutMediator(binding.tlTOP100, binding.vpTOP100, this::setUpTabTitles).attach();

        binding.vpTOP100.setOffscreenPageLimit(3);

    }

    private void setUpTabTitles(TabLayout.Tab tab, int position) {
        String[] tabTitles = getResources().getStringArray(R.array.TOP100);
        if (position < tabTitles.length) {
            tab.setText(tabTitles[position]);
        }
    }
}
