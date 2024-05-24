package com.example.toptoon.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.toptoon.api.NetworkManager;
import com.example.toptoon.dto.ApiItems;
import com.example.toptoon.dto.BaseContentItem;
import com.example.toptoon.ui.adapters.NewProductRvAdapter;
import com.example.toptoon.WebViewActivity;
import com.example.toptoon.databinding.FragmentNewProductBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewProductFragment extends Fragment {

    private NewProductRvAdapter adapter;
    FragmentNewProductBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewProductBinding.inflate(inflater, container, false);
        initializeComponents();
        fetch();
        return binding.getRoot();
    }

    public void initializeComponents() {
        binding.rvComplete.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new NewProductRvAdapter(this::onItemClick);
        binding.rvComplete.setAdapter(adapter);
    }

    public void fetch() {
        NetworkManager.fetchTopToonItems(new Callback<ApiItems>() {
            @Override
            public void onResponse(@NonNull Call<ApiItems> call, @NonNull Response<ApiItems> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Integer> ids = response.body().getNewProduct();
                    List<ApiItems.Webtoon> webtoons = response.body().getWebtoons();
                    List<ApiItems.Webtoon> filteredWebtoons = new ArrayList<>();
                    for (Integer id : ids) {
                        for (ApiItems.Webtoon webtoon : webtoons) {
                            if (webtoon.getId() == id) {
                                filteredWebtoons.add(webtoon);
                            }
                        }
                    }
                    displayData(filteredWebtoons);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiItems> call, Throwable t) {
                Log.e("NewProductFragment", "Failed to fetch data: " + t.getMessage());
            }
        });
    }

    private void displayData(List<ApiItems.Webtoon> tabItems) {
        if (tabItems != null) {
            List<BaseContentItem> items = new ArrayList<>();
            for (ApiItems.Webtoon item : tabItems) {
                items.add(new BaseContentItem(
                        item.getTitle(),
                        item.getAuthor(),
                        item.getLatestEpisode(),
                        item.getViews(),
                        item.isNew(),
                        item.isDiscounted(),
                        item.isExclusive(),
                        item.isWaitFree(),
                        item.isRecentlyUpdated(),
                        item.getImageUrl(),
                        item.getSlug()
                ));
            }
            adapter.submitList(items);
            Log.println(Log.INFO, "TabNewItem", "Tab items are not null" + items);
        } else {
            Log.e("TabNewItem", "Tab items are null");
        }
    }

    private void onItemClick(BaseContentItem item) {
        // 식별자를 사용해 웹뷰로 이동
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        String url = "https://toptoon.com/comic/ep_list/" + item.getSlug();
        intent.putExtra("URL", url);
        startActivity(intent);
    }

}
