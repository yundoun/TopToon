package com.example.toptoon.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.toptoon.databinding.FragmentAllAgesBinding;

public class AllAgeFragment extends Fragment {

    FragmentAllAgesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAllAgesBinding.inflate(inflater, container, false);
        initializeComponents();
        return binding.getRoot();
    }

    private void initializeComponents() {
    }

}
