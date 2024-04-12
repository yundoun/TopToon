package com.example.toptoon;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.toptoon.databinding.FragmentTab1Binding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabFragment1 extends Fragment {
    private FragmentTab1Binding binding;
    private TabRvAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTab1Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        fetchDataFromNetwork();
    }

    private void setupRecyclerView() {
        adapter = new TabRvAdapter();
        binding.rvTab.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvTab.setAdapter(adapter);
    }

    private void fetchDataFromNetwork() {
        NetworkManager.fetchTopToonItems(new Callback<TopToonItems>() {
            @Override
            public void onResponse(@NonNull Call<TopToonItems> call, @NonNull Response<TopToonItems> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.println(Log.INFO, "TabFragment1", "데이터를 받아옴");
                    displayData(response.body().getTabRealTime());
                } else {
                    Log.e("TabFragment1", "tabItems가 null입니다");
                }
            }

            @Override
            public void onFailure(@NonNull Call<TopToonItems> call, @NonNull Throwable t) {
                Log.e("TabFragment1", "Failed to fetch data: " + t.getMessage());
            }
        });
    }

    private void displayData(List<TopToonItems.TabItem> tabItems) {
        if (tabItems != null) {
            List<TabContentItem> items = new ArrayList<>();
            for (TopToonItems.TabItem item : tabItems) {
                items.add(new TabContentItem(
                        item.getTitle(),
                        item.getAuthor(),
                        item.getLatestEpisode(),
                        item.getViews(),
                        item.isNew(),
                        item.isDiscounted(),
                        item.isExclusive(),
                        item.isWaitFree(),
                        item.isRecentlyUpdated(),
                        item.getImageUrl()
                ));
            }
            adapter.submitList(items);
        } else {
            Log.e("TabFragment1", "Tab items are null");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // 뷰 바인딩 참조 해제
    }
}
