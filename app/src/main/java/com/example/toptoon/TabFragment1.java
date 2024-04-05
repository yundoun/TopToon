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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 프래그먼트 레이아웃을 인플레이트합니다.
            binding = FragmentTab1Binding.inflate(inflater, container, false);
            return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TopToonApi service = RetrofitClient.getClient().create((TopToonApi.class));
        Call<TopToonItems> call = service.getTopToonItems();

        call.enqueue(new Callback<TopToonItems>() {
            @Override
            public void onResponse(Call<TopToonItems> call, Response<TopToonItems> response) {
                if (response.isSuccessful() && response.body() != null){
                    Log.println(Log.INFO, "TabFragment1", "데이터를 받아옴");

                    List<TopToonItems.TabItem> tabItems = response.body().getTab();

                    Log.println(Log.INFO, "TabFragment1", "tabItems.size(): " + tabItems.size());

                    List<TabContentItem> items = new ArrayList<>();

                    // 받아온 데이터를 TabContentItem 객체로 변환하고 rank를 설정
                    for (int i = 0; i < tabItems.size(); i++) {
                        TopToonItems.TabItem item = tabItems.get(i);
                        items.add(new TabContentItem(
                                item.getTitle(),
                                item.getAuthor(),
                                item.getLatest_episode(),
                                item.getViews(),
                                item.isIs_new(),
                                item.isIs_discounted(),
                                item.isIs_exclusive(),
                                item.isWait_free(),
                                item.isRecently_updated(),
                                item.getImage_url()
                        ));
                    }

                    // 어댑터 생성 및 RecyclerView에 설정
                    TabRvAdapter adapter = new TabRvAdapter(items);
                    binding.rvTab.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.rvTab.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<TopToonItems> call, Throwable t) {
                Log.e("TabFragment1", "데이터를 받아오는 데 실패함", t);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // 뷰 바인딩 참조 해제
    }
}
