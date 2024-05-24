package com.example.toptoon.fragment.tap;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.toptoon.api.NetworkManager;
import com.example.toptoon.dto.ApiItems;
import com.example.toptoon.dto.BaseContentItem;
import com.example.toptoon.ui.adapters.TabRvAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabRealTime extends BaseTabFragment {
    @Override
    protected TabRvAdapter createAdapter() { return new TabRvAdapter(this::onItemClick); }

    protected void fetchDataFromNetwork() {
        NetworkManager.fetchTopToonItems(new Callback<ApiItems>() {
            @Override
            public void onResponse(@NonNull Call<ApiItems> call, @NonNull Response<ApiItems> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.println(Log.INFO, "TabRealTime", "데이터를 받아옴");
                    List<Integer> tabRealTimeIds = response.body().getTabRealTime();
                    List<ApiItems.Webtoon> allWebtoons = response.body().getWebtoons();
                    List<ApiItems.Webtoon> filteredWebtoons = new ArrayList<>();
                    for (Integer id : tabRealTimeIds) {
                        for (ApiItems.Webtoon webtoon : allWebtoons) {
                            if (webtoon.getId() == id) {
                                filteredWebtoons.add(webtoon);
                            }
                        }
                    }
                    displayData(filteredWebtoons);
                } else {
                    Log.e("TabRealTime", "Response is not successful or body is null");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiItems> call, @NonNull Throwable t) {
                Log.e("TabRealTime", "Failed to fetch data: " + t.getMessage());
            }
        });
    }

    private void displayData(List<ApiItems.Webtoon> tabItems) {
        if (tabItems != null && !tabItems.isEmpty()) {
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
        } else {
            Log.e("TabRealTime", "Tab items are null or empty");
        }
    }
}
