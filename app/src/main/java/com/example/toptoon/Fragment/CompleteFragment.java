package com.example.toptoon.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.toptoon.Management.CompleteManager;
import com.example.toptoon.R;
import com.example.toptoon.databinding.FragmentCompleteBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CompleteFragment extends Fragment {

    FragmentCompleteBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCompleteBinding.inflate(inflater, container, false);
        initializeComponents();
        return binding.getRoot();
    }

    public void initializeComponents() {
        binding.vpComplete.setAdapter(new CompleteManager(this));
        new TabLayoutMediator(binding.tlComplete, binding.vpComplete, this::setUpTabTitles).attach();

        binding.vpComplete.setOffscreenPageLimit(4);
    }

    private void setUpTabTitles(TabLayout.Tab tab, int position) {
        String[] tabTitles = getResources().getStringArray(R.array.complete);
        if (position < tabTitles.length) {
            tab.setText(tabTitles[position]);
        }
    }

}
