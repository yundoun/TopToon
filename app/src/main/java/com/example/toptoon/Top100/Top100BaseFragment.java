package com.example.toptoon.Top100;

import android.os.Bundle;
import android.util.Log;

import com.example.toptoon.Api.NetworkManager;
import com.example.toptoon.DataModel.ApiItems;
import com.example.toptoon.DataModel.BaseContentItem;
import com.example.toptoon.Fragment.BaseMainListFragment;
import com.example.toptoon.Ui.MainListRvAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Top100BaseFragment extends BaseMainListFragment {

    private static final String ARG_TYPE = "type";
    private int type;

    public static Top100BaseFragment newInstance(int type) {
        Top100BaseFragment fragment = new Top100BaseFragment();
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
        return new MainListRvAdapter();
    }

    @Override
    protected void fetchDataFromNetwork() {
        NetworkManager.fetchTopToonItems(new Callback<ApiItems>() {
            @Override
            public void onResponse(Call<ApiItems> call, Response<ApiItems> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Integer> weekIds = response.body().getTop100().getWeek();
                    List<Integer> monthIds = response.body().getTop100().getMonth();
                    List<Integer> newWebtoonIds = response.body().getTop100().getNewWebtoon();
                    List<Integer> saleWebtoonIds = response.body().getTop100().getSale();
                    List<ApiItems.Webtoon> webtoons = response.body().getWebtoons();

                    Log.println(Log.DEBUG, "Top100BaseFragment", "weekIds: " + weekIds);

                    List<Integer> selectedIds = new ArrayList<>();
                    switch (type) {
                        case 0: // Weekly
                            if (weekIds != null) selectedIds.addAll(weekIds);
                            break;
                        case 1: // Monthly
                            if (monthIds != null) selectedIds.addAll(monthIds);
                            break;
                        case 2: // New
                            if (newWebtoonIds != null) selectedIds.addAll(newWebtoonIds);
                            break;
                        case 3: // Sale
                            if (saleWebtoonIds != null) selectedIds.addAll(saleWebtoonIds);
                            break;
                    }

                    List<BaseContentItem> baseContentItems = filterAndConvertWebtoons(webtoons, selectedIds);
                    adapter.submitList(baseContentItems);
                }
            }

            @Override
            public void onFailure(Call<ApiItems> call, Throwable t) {

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
}
