package com.example.toptoon.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.toptoon.ui.adapters.MainListRvAdapter;
import com.example.toptoon.databinding.FragmentMainListBinding;

public abstract class BaseMainListFragment extends Fragment {
    protected FragmentMainListBinding binding;
    protected MainListRvAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        fetchDataFromNetwork();
    }

    private void setupRecyclerView() {
        adapter = createAdapter();
        binding.rvMainList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        binding.rvMainList.setAdapter(adapter);
    }

    protected abstract MainListRvAdapter createAdapter();

    protected abstract void fetchDataFromNetwork();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // 뷰 바인딩 참조 해제
    }
}
