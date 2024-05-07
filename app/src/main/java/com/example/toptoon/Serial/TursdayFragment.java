package com.example.toptoon.Serial;

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
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TursdayFragment extends BaseMainListFragment {
    @Override
    protected MainListRvAdapter createAdapter() {
        return  new MainListRvAdapter();
    }

    @Override
    protected void fetchDataFromNetwork() {
        NetworkManager.fetchTopToonItems(new Callback<ApiItems>() {
            @Override
            public void onResponse(Call<ApiItems> call, Response<ApiItems> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Integer> tursdayIds = response.body().getSerial().getThursday();
                    List<ApiItems.Webtoon> webtoons = response.body().getWebtoons();
                    List<BaseContentItem> baseContentItems = filterTabContentItemsByWebtoonIds(tursdayIds, webtoons);

                    adapter.submitList(baseContentItems);
                }
            }

            @Override
            public void onFailure(Call<ApiItems> call, Throwable t) {
                Log.e("NetworkError", "네트워크 호출 실패", t);
            }
        });
    }

    private List<BaseContentItem> filterTabContentItemsByWebtoonIds(List<Integer> webtoonIds, List<ApiItems.Webtoon> webtoons) {
        //Set은 중복된 값을 허용하지 않으며, 순서가 없다. 시간 복잡도 O(1)로 중복을 제거할 수 있다.
        Set<Integer> idSet = webtoonIds.stream().collect(Collectors.toSet());
        List<BaseContentItem> items = new ArrayList<>();
        for (ApiItems.Webtoon webtoon : webtoons) {
            if (idSet.contains(webtoon.getId())) {
                items.add(new BaseContentItem(
                        webtoon.getTitle(),
                        webtoon.getAuthor(),
                        webtoon.getLatestEpisode(),
                        webtoon.getViews(),
                        webtoon.isNew(),
                        webtoon.isDiscounted(),
                        webtoon.isExclusive(),
                        webtoon.isWaitFree(),
                        webtoon.isRecentlyUpdated(),
                        webtoon.getImageUrl()
                ));
            }
        }
        return items;
    }
}
