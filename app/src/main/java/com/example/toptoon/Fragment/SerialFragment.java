package com.example.toptoon.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.toptoon.R;
import com.example.toptoon.Management.SerialListManager;
import com.example.toptoon.databinding.FragmentSerialBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class SerialFragment extends Fragment {

    FragmentSerialBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSerialBinding.inflate(inflater, container, false);
        initializeComponents();
        return binding.getRoot();
    }

    private void initializeComponents() {
        binding.vpSerial.setAdapter(new SerialListManager(this));
        new TabLayoutMediator(binding.tlSerial, binding.vpSerial, this::setUpTabTitles).attach();
    }

    private void setUpTabTitles(TabLayout.Tab tab, int position) {
        String[] tabTitles = getResources().getStringArray(R.array.serial);
        if (position < tabTitles.length) {
            tab.setText(tabTitles[position]);
        }
    }
}
