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

import com.example.toptoon.databinding.FragmentTab3Binding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabSale3 extends Fragment {
    private FragmentTab3Binding binding;
    private TabRvAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 프래그먼트 레이아웃을 인플레이트합니다.
        binding = FragmentTab3Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        fetchDataFromNetwork();
    }

    private void setupRecyclerView() {
        // 리사이클러뷰 어댑터를 생성합니다.
        adapter = new TabRvAdapter();
        // 리사이클러뷰에 레이아웃 매니저를 설정합니다.
        binding.rvTab.setLayoutManager(new LinearLayoutManager(getContext()));
        // 리사이클러뷰에 어댑터를 설정합니다.
        binding.rvTab.setAdapter(adapter);
    }

    private void fetchDataFromNetwork() {
        // 네트워크로부터 데이터를 가져옵니다.
        NetworkManager.fetchTopToonItems(new Callback<TopToonItems>() {
            @Override
            public void onResponse(@NonNull Call<TopToonItems> call, @NonNull Response<TopToonItems> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 데이터를 성공적으로 받아왔을 때
                    Log.println(Log.INFO, "TabFragment3", "데이터를 받아옴");
                    // 데이터를 화면에 표시합니다.
                    displayData(response.body().getTabSale());
                } else {
                    // 데이터를 받아오지 못했을 때
                    Log.e("TabFragment3", "tabItems가 null입니다");
                }
            }

            @Override
            public void onFailure(@NonNull Call<TopToonItems> call, @NonNull Throwable t) {
                // 네트워크 요청이 실패했을 때
                Log.e("TabFragment3", "네트워크 요청이 실패했습니다");
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
            Log.e("TabFragment3", "Tab items are null");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // 뷰 바인딩 참조 해제
    }

}