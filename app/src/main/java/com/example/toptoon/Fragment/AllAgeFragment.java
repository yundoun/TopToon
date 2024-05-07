package com.example.toptoon.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.toptoon.DataModel.MainListItem;
import com.example.toptoon.R;
import com.example.toptoon.Ui.MainListRvAdapter;
import com.example.toptoon.databinding.FragmentAllAgesBinding;

import java.util.ArrayList;
import java.util.List;

public class AllAgeFragment extends Fragment {

    FragmentAllAgesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAllAgesBinding.inflate(inflater, container, false);
        initializeComponents();
        return binding.getRoot();
    }

    private void initializeComponents() {
        binding.rvAllAges.setLayoutManager(new GridLayoutManager(getContext(), 3));
        MainListRvAdapter adapter = new MainListRvAdapter();
        binding.rvAllAges.setAdapter(adapter);
    }

}