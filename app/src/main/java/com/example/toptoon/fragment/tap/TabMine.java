package com.example.toptoon.fragment.tap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.toptoon.databinding.FragmentTabMineBinding;

public class TabMine extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        com.example.toptoon.databinding.FragmentTabMineBinding binding = FragmentTabMineBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
