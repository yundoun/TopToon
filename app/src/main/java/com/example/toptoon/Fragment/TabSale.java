package com.example.toptoon.Fragment;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.toptoon.Api.NetworkManager;
import com.example.toptoon.DataModel.ApiItems;
import com.example.toptoon.DataModel.BaseContentItem;
import com.example.toptoon.Ui.TabRvAdapter;

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
        NetworkManager.fetchTopToonItems(new Callback<ApiItems>() {
            @Override
            public void onResponse(@NonNull Call<ApiItems> call, @NonNull Response<ApiItems> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.println(Log.INFO, "TabSale", "데이터를 받아옴");
                    List<Integer> tabSaleIds = response.body().getTabSale();
                    List<ApiItems.Webtoon> allWebtoons = response.body().getWebtoons();
                    List<ApiItems.Webtoon> filteredWebtoons = new ArrayList<>();
                    for (Integer id : tabSaleIds) {
                        for (ApiItems.Webtoon webtoon : allWebtoons) {
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
            public void onFailure(@NonNull Call<ApiItems> call, @NonNull Throwable t) {
                // 네트워크 요청이 실패했을 때
                Log.e("TabSale", "네트워크 요청이 실패했습니다");
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
                        item.getImageUrl()
                ));
            }
            adapter.submitList(items);
        } else {
            Log.e("TabSale", "Tab items are null");
        }
    }
}