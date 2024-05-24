package com.example.toptoon.fragment.serial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.toptoon.R;
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

public class DayFragment extends BaseMainListFragment {
    private String day;

    public static DayFragment newInstance(String day) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putString("day", day);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            day = getArguments().getString("day");
        }
    }

    @Override
    protected MainListRvAdapter createAdapter() {
        return new MainListRvAdapter(this::onItemClick);
    }

    private void onItemClick(BaseContentItem item) {
        // 식별자를 사용해 웹뷰로 이동
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        String url = "https://toptoon.com/comic/ep_list/" + item.getSlug();
        intent.putExtra("URL", url);
        startActivity(intent);
    }

    @Override
    protected void fetchDataFromNetwork() {
        NetworkManager.fetchTopToonItems(new Callback<ApiItems>() {
            @Override
            public void onResponse(@NonNull Call<ApiItems> call, @NonNull Response<ApiItems> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Integer> dayIds = getIdsByDay(response.body().getSerial(), day, getContext());
                    List<ApiItems.Webtoon> webtoons = response.body().getWebtoons();
                    List<BaseContentItem> baseContentItems = filterTabContentItemsByWebtoonIds(dayIds, webtoons);

                    Log.println(Log.DEBUG, "DayFragment", "dayIds: " + dayIds);

                    adapter.submitList(baseContentItems);

                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiItems> call, @NonNull Throwable t) {
                Log.e("NetworkError", "네트워크 호출 실패", t);
            }
        });
    }

    private List<BaseContentItem> filterTabContentItemsByWebtoonIds(List<Integer> webtoonIds, List<ApiItems.Webtoon> webtoons) {
        if (webtoonIds == null || webtoons == null) {
            Log.e("FilterError", "Received null webtoonIds or webtoons list");
            return new ArrayList<>();  // 오류 발생 시 빈 리스트 반환
        }

        Set<Integer> idSet = new HashSet<>(webtoonIds);  // ID 리스트를 Set으로 변환하여 중복을 제거
        List<BaseContentItem> items = new ArrayList<>();
        for (ApiItems.Webtoon webtoon : webtoons) {
            if (idSet.contains(webtoon.getId())) {  // Set에 웹툰 ID가 존재하는지 확인
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
                        webtoon.getImageUrl(),
                        webtoon.getSlug()
                ));
            }
        }
        return items;  // 필터링된 결과를 반환
    }

    private List<Integer> getIdsByDay(ApiItems.Serial serial, String day, Context context) {

        if (context == null) {
            return new ArrayList<>();
        }

        String [] serialArray = context.getResources().getStringArray(R.array.serial);

        if (day.equals(serialArray[0])) {
            return serial.getMonday();
        } else if (day.equals(serialArray[1])) {
            return serial.getTuesday();
        } else if (day.equals(serialArray[2])) {
            return serial.getWednesday();
        } else if (day.equals(serialArray[3])) {
            return serial.getThursday();
        } else if (day.equals(serialArray[4])) {
            return serial.getFriday();
        } else if (day.equals(serialArray[5])) {
            return serial.getSaturday();
        } else if (day.equals(serialArray[6])) {
            return serial.getSunday();
        } else if (day.equals(serialArray[7])) {
            return serial.getRemake();
        } else {
            return new ArrayList<>();
        }
    }
}
