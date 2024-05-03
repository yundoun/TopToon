package com.example.toptoon.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.toptoon.databinding.FragmentEventBinding;

public class EventFragment extends Fragment {
    FragmentEventBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEventBinding.inflate(inflater, container, false);
        initializeComponents();
        return binding.getRoot();
    }

    private void initializeComponents() {

    }

}
