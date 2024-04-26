package com.example.toptoon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.toptoon.databinding.FragmentMondayBinding;

public class MondayFragment extends Fragment {

    FragmentMondayBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMondayBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

}
