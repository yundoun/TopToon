package com.example.toptoon.fragment.complete;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.toptoon.api.NetworkManager;
import com.example.toptoon.dto.ApiItems;
import com.example.toptoon.dto.BaseContentItem;
import com.example.toptoon.fragment.BaseMainListFragment;
import com.example.toptoon.ui.adapters.MainListRvAdapter;
import com.example.toptoon.WebViewActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseCompleteFragment extends BaseMainListFragment {

    private static final String ARG_TYPE = "type";
    private int type;

    public static BaseCompleteFragment newInstance(int type) {
        BaseCompleteFragment fragment = new BaseCompleteFragment();
        fragment.type = type;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(ARG_TYPE);
        }
    }

    @Override
    protected MainListRvAdapter createAdapter() {
        return new MainListRvAdapter(this::onItemClick);
    }

    @Override
    protected void fetchDataFromNetwork() {
        NetworkManager.fetchTopToonItems(new Callback<ApiItems>() {
            @Override
            public void onResponse(@NonNull Call<ApiItems> call, @NonNull Response<ApiItems> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Integer> recentIds = response.body().getComplete().getRecent();
                    List<Integer> higshestSalesIds = response.body().getComplete().getHighestSales();
                    List<Integer> yearIds = response.body().getComplete().getYear();
                    List<Integer> genreIds = response.body().getComplete().getGenre();
                    List<ApiItems.Webtoon> webtoons = response.body().getWebtoons();


                    List<Integer> selectedIds = new ArrayList<>();
                    switch (type) {
                        case 0: // recent
                            if (recentIds != null) selectedIds.addAll(recentIds);
                            break;
                        case 1: // highestSales
                            if (higshestSalesIds != null) selectedIds.addAll(higshestSalesIds);
                            break;
                        case 2: // year
                            if (yearIds != null) selectedIds.addAll(yearIds);
                            break;
                        case 3: // genre
                            if (genreIds != null) selectedIds.addAll(genreIds);
                            break;
                    }

                    List<BaseContentItem> baseContentItems = filterAndConvertWebtoons(webtoons, selectedIds);
                    adapter.submitList(baseContentItems);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiItems> call, @NonNull Throwable t) {
                Log.e("NetworkError", "네트워크 호출 실패", t);
            }
        });
    }

    private List<BaseContentItem> filterAndConvertWebtoons(List<ApiItems.Webtoon> webtoons, List<Integer> selectedIds) {
        Set<Integer> idSet = new HashSet<>(selectedIds);

        List<BaseContentItem> filteredWebtoons = new ArrayList<>();
        for (ApiItems.Webtoon webtoon : webtoons) {
            if (idSet.contains(webtoon.getId())) {
                filteredWebtoons.add(new BaseContentItem(
                        webtoon.getTitle(),
                        webtoon.getAuthor(),
                        webtoon.getLatestEpisode(),
                        webtoon.getViews(),
                        webtoon.isNew(),
                        webtoon.isDiscounted(),
                        webtoon.isExclusive(),
                        webtoon.isWaitFree(),
                        webtoon.isRecentlyUpdated(),
                        webtoon.getImageUrl(),
                        webtoon.getSlug()
                ));
            }
        }
        return filteredWebtoons;
    }

    private void onItemClick(BaseContentItem item) {
        // 식별자를 사용해 웹뷰로 이동
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        String url = "https://toptoon.com/comic/ep_list/" + item.getSlug();
        intent.putExtra("URL", url);
        startActivity(intent);
    }
}
