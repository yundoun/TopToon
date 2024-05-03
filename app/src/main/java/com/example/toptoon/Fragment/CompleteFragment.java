package com.example.toptoon.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.toptoon.Ui.MainListRvAdapter;
import com.example.toptoon.R;
import com.example.toptoon.DataModel.SerailListItem;
import com.example.toptoon.databinding.CompleteFragmentBinding;

import java.util.ArrayList;
import java.util.List;

public class CompleteFragment extends Fragment {

    CompleteFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = CompleteFragmentBinding.inflate(inflater, container, false);
        initializeComponents();
        return binding.getRoot();
    }

    public void initializeComponents() {
        binding.rvComplete.setLayoutManager(new GridLayoutManager(getContext(), 3));
        MainListRvAdapter adapter = new MainListRvAdapter();
        binding.rvComplete.setAdapter(adapter);

        // 테스트 데이터 생성
        List<SerailListItem> items = new ArrayList<>();
        items.add(new SerailListItem("무련전봉", R.drawable.tmp, "제1200화", "329만"));
        items.add(new SerailListItem("무련전봉", R.drawable.tmp, "제1200화", "329만"));
        items.add(new SerailListItem("무련전봉", R.drawable.tmp, "제1200화", "329만"));
        items.add(new SerailListItem("무련전봉", R.drawable.tmp, "제1200화", "329만"));
        items.add(new SerailListItem("무련전봉", R.drawable.tmp, "제1200화", "329만"));
        adapter.submitList(items);

    }

}
