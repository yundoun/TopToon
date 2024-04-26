package com.example.toptoon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.toptoon.databinding.FragmentSerialBinding;

public class SerialFragment extends Fragment {

    FragmentSerialBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSerialBinding.inflate(inflater, container, false);
        //initializeComponents();
        return binding.getRoot();
    }

//    private void initializeComponents() {
//        binding.vpWeek.setAdapter(new MainMenuManager(this));
//        new TabLayoutMediator(binding.tlWeek, binding.vpWeek, this::setUpTabTitles).attach();
//    }
//
//    private void setUpTabTitles(TabLayout.Tab tab, int position) {
//        String[] tabTitles = getResources().getStringArray(R.array.weekdays);
//        if (position < tabTitles.length) {
//            tab.setText(tabTitles[position]);
//        }
//    }
}
