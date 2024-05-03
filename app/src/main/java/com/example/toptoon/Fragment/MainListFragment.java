package com.example.toptoon.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.toptoon.MainListRvAdapter;
import com.example.toptoon.R;
import com.example.toptoon.DataModel.SerailListItem;
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

        List<SerailListItem> items = new ArrayList<>();
        items.add(new SerailListItem("무련전봉", R.drawable.tmp, "제1200화", "329만"));
        items.add(new SerailListItem("무련전봉", R.drawable.tmp, "제1200화", "329만"));
        items.add(new SerailListItem("무련전봉", R.drawable.tmp, "제1200화", "329만"));
        items.add(new SerailListItem("무련전봉", R.drawable.tmp, "제1200화", "329만"));
        items.add(new SerailListItem("무련전봉", R.drawable.tmp, "제1200화", "329만"));
        items.add(new SerailListItem("무련전봉", R.drawable.tmp, "제1200화", "329만"));
        items.add(new SerailListItem("무련전봉", R.drawable.tmp, "제1200화", "329만"));
        adapter.submitList(items); // 테스트 아이템 리스트를 어댑터에 제출
    }
}

