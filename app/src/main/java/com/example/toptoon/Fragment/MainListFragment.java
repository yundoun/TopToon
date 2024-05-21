package com.example.toptoon.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.toptoon.DataModel.BaseContentItem;
import com.example.toptoon.DataModel.HorizontalContentItem;
import com.example.toptoon.Ui.MainListRvAdapter;
import com.example.toptoon.WebViewActivity;
import com.example.toptoon.databinding.FragmentMainListBinding;

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
        MainListRvAdapter adapter = new MainListRvAdapter(this::onItemClick);
        binding.rvMainList.setAdapter(adapter);
    }

    private void onItemClick(BaseContentItem item) {
        // 식별자를 사용해 웹뷰로 이동
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        String url = "https://toptoon.com/comic/ep_list/" + item.getSlug();
        intent.putExtra("URL", url);
        startActivity(intent);
    }
}

