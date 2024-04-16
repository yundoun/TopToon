package com.example.toptoon;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabNewItem extends BaseTabFragment {

    @Override
    protected TabRvAdapter createAdapter() {
        return new TabRvAdapter();
    }

    protected void fetchDataFromNetwork() {
        NetworkManager.fetchTopToonItems(new Callback<TopToonItems>() {
            @Override
            public void onResponse(@NonNull Call<TopToonItems> call, @NonNull Response<TopToonItems> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.println(Log.INFO, "TabNewItem", "데이터를 받아옴");
                    List<Integer> tabNewItems = response.body().getTabNew();
                    List<TopToonItems.Webtoon> allWebtoons = response.body().getWebtoons();
                    List<TopToonItems.Webtoon> filteredWebtoons = new ArrayList<>();
                    for(Integer id : tabNewItems) {
                        for(TopToonItems.Webtoon webtoon : allWebtoons) {
                            if(webtoon.getId() == id) {
                                filteredWebtoons.add(webtoon);
                            }
                        }
                    }
                    displayData(filteredWebtoons);
                } else {
                    Log.e("TabNewItem", "tabItems가 null입니다");
                }
            }

            @Override
            public void onFailure(@NonNull Call<TopToonItems> call, @NonNull Throwable t) {
                Log.e("TabNewItem", "데이터를 받아오지 못함", t);
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
            Log.e("TabNewItem", "Tab items are null");
        }
    }
}