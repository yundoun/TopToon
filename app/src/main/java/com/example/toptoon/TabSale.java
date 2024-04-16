package com.example.toptoon;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabSale extends BaseTabFragment {

    @Override
    protected TabRvAdapter createAdapter() {
        return new TabRvAdapter();
    }

    protected void fetchDataFromNetwork() {
        NetworkManager.fetchTopToonItems(new Callback<TopToonItems>() {
            @Override
            public void onResponse(@NonNull Call<TopToonItems> call, @NonNull Response<TopToonItems> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.println(Log.INFO, "TabSale", "데이터를 받아옴");
                    List<Integer> tabSaleIds = response.body().getTabSale();
                    List<TopToonItems.Webtoon> allWebtoons = response.body().getWebtoons();
                    List<TopToonItems.Webtoon> filteredWebtoons = new ArrayList<>();
                    for (Integer id : tabSaleIds) {
                        for (TopToonItems.Webtoon webtoon : allWebtoons) {
                            if (webtoon.getId() == id) {
                                filteredWebtoons.add(webtoon);
                            }
                        }
                    }
                    displayData(filteredWebtoons);
                } else {
                    // 데이터를 받아오지 못했을 때
                    Log.e("TabSale", "tabItems가 null입니다");
                }
            }

            @Override
            public void onFailure(@NonNull Call<TopToonItems> call, @NonNull Throwable t) {
                // 네트워크 요청이 실패했을 때
                Log.e("TabSale", "네트워크 요청이 실패했습니다");
            }
        });
    }

    private void displayData(List<TopToonItems.Webtoon> tabItems) {
        if (tabItems != null) {
            List<TabContentItem> items = new ArrayList<>();
            for (TopToonItems.Webtoon item : tabItems) {
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
            Log.e("TabSale", "Tab items are null");
        }
    }
}