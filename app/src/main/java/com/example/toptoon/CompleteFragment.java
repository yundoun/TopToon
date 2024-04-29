package com.example.toptoon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.toptoon.databinding.CompleteFragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class CompleteFragment extends Fragment {

    CompleteFragmentBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = CompleteFragmentBinding.inflate(inflater, container, false);
        initializeComponents();
        return binding.getRoot();
    }

    public void initializeComponents() {
        binding.rvComplete.setLayoutManager(new GridLayoutManager(getContext(), 3));
        MainListRvAdapter adapter = new MainListRvAdapter();
        binding.rvComplete.setAdapter(adapter);

        List<SerailListItem> items = new ArrayList<>();
        items.add(new SerailListItem("무련전봉", R.drawable.tmp, "제1200화", "329만"));
        items.add(new SerailListItem("무련전봉", R.drawable.tmp, "제1200화", "329만"));
        items.add(new SerailListItem("무련전봉", R.drawable.tmp, "제1200화", "329만"));
        items.add(new SerailListItem("무련전봉", R.drawable.tmp, "제1200화", "329만"));
        items.add(new SerailListItem("무련전봉", R.drawable.tmp, "제1200화", "329만"));
        adapter.submitList(items); // 테스트 아이템 리스트를 어댑터에 제출

    }

}
