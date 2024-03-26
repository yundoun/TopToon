package com.example.toptoon;

import android.os.Bundle;
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

        // 데이터 리스트 생성 및 샘플 데이터 추가
        List<TabContentItem> items = new ArrayList<>();
        items.add(new TabContentItem(R.drawable.tab_live_1, "1", "제407화", "19만", "마황의 귀환"));
        items.add(new TabContentItem(R.drawable.tab_live_1, "2", "제61화 최종화", "309만", "실연 전당포"));
        items.add(new TabContentItem(R.drawable.tab_live_1, "3", "제319화", "57만", "레벨업에 미친 의사"));
        items.add(new TabContentItem(R.drawable.tab_live_1, "4", "시즌2 제149화", "3,500만", "청소부 K"));
        items.add(new TabContentItem(R.drawable.tab_live_1, "5", "제518화", "4.0만", "무도독존"));
        items.add(new TabContentItem(R.drawable.tab_live_1, "6", "제524화", "23만", "요신기"));

        // 어댑터 생성 및 RecyclerView에 설정
        TabRvAdapter adapter = new TabRvAdapter(items);
        binding.rvTab.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvTab.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // 뷰 바인딩 참조 해제
    }
}
