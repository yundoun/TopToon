package com.example.toptoon.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.toptoon.DataModel.BaseContentItem;
import com.example.toptoon.Ui.TabRvAdapter;
import com.example.toptoon.WebViewActivity;
import com.example.toptoon.databinding.FragmentTabItemBinding;

public abstract class BaseTabFragment extends Fragment {
    protected FragmentTabItemBinding binding;
    protected TabRvAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTabItemBinding.inflate(inflater, container, false);
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
        binding.rvTab.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvTab.setAdapter(adapter);
    }

    protected abstract TabRvAdapter createAdapter();

    protected abstract void fetchDataFromNetwork();

    protected void onItemClick(BaseContentItem item) {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        String url = "https://toptoon.com/comic/ep_list/" + item.getSlug();
        intent.putExtra("URL", url);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // 뷰 바인딩 참조 해제
    }
}
