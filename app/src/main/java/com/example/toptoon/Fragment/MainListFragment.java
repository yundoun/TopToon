package com.example.toptoon.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.toptoon.Ui.MainListRvAdapter;
import com.example.toptoon.R;
import com.example.toptoon.DataModel.MainListItem;
import com.example.toptoon.databinding.FragmentMainListBinding;

import java.util.ArrayList;
import java.util.List;

public class MainListFragment extends Fragment {

    FragmentMainListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainListBinding.inflate(inflater, container, false);
        initializeRecyclerViews();
        return binding.getRoot();
    }

    private void initializeRecyclerViews() {
        binding.rvMainList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        Log.println(Log.INFO, "MainListFragment", "initializeRecyclerViews: " + binding.rvMainList.getLayoutManager());
        MainListRvAdapter adapter = new MainListRvAdapter();
        binding.rvMainList.setAdapter(adapter);
    }
}

