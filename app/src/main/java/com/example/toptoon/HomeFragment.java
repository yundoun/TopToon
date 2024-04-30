package com.example.toptoon;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.toptoon.databinding.FragmentHomeBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private ViewPager2 vpAutoSlide;
    private final Handler sliderHandler = new Handler();
    private int currentItem = 0;
    private FragmentHomeBinding binding;
    private CommonRvAdapter adapterFreeWait;
    private CommonRvAdapter adapterOneCoin;
    private CommonRvAdapter adapterCustomKeyword;
    private CommonRvAdapter adapterRecommendGenre;
    List<String> slideImageUrls = new ArrayList<>();
    List<String> eventImageUrls = new ArrayList<>();
    private Map<String, String> customKeywordTagToJsonKey, recommendGenreTagToJsonKey;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initializeComponents();
        return binding.getRoot();
    }

    private void initializeComponents() {
        fetchSlideAds();
        setTabColor();
        setupTabLayoutWithViewPager();
        setupAutoSlide();
        setSectionAd();
        initializeRecyclerViews();
        fetchAndDisplayCommonRecyclerView();
        setFreeAd();
        setupTagMenu();

        fetchWebtoonsForTag("#인기작품");
        fetchWebtoonsForTag("#로맨스");

    }

    private void fetchSlideAds() {
        NetworkManager.fetchTopToonItems(new Callback<TopToonItems>() {
            @Override
            public void onResponse(@NonNull Call<TopToonItems> call, @NonNull Response<TopToonItems> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TopToonItems items = response.body();
                    Log.println(Log.INFO, "HomeFragment", "데이터를 받아오는 데 성공함");

                    for (TopToonItems.SlideAd slideAd : items.getSlideAd()) {
                        slideImageUrls.add(slideAd.getImageUrl());
                    }

                    for (TopToonItems.Event event : items.getEvent()) {
                        eventImageUrls.add(event.getImageUrl());
                    }
                    initializeSlider(slideImageUrls);
                    setEventAd(eventImageUrls);
                } else {
                    Log.e("HomeFragment", "응답 실패: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TopToonItems> call, @NonNull Throwable t) {
                Log.println(Log.ERROR, "HomeFragment", "데이터를 받아오는 데 실패함");
            }
        });
    }

    private void setTabColor() {
        TabLayout.Tab firstTab = binding.tabLayout.getTabAt(0);
        if (firstTab != null) {
            firstTab.view.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.light_pink));
        }

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.categoryViewPager.setCurrentItem(tab.getPosition());
                tab.view.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.light_pink));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.view.setBackgroundColor(ContextCompat.getColor(binding.getRoot().getContext(), R.color.white));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // 설정 x
            }
        });
    }

    private void initializeSlider(List<String> ImageUrls) {

        if (!isAdded() || binding == null) {
            return;
        }

        vpAutoSlide = binding.vpAutoSlide;
        SlideImageAdapter adapter1 = new SlideImageAdapter(getContext(), ImageUrls);
        vpAutoSlide.setAdapter(adapter1);

        int imagesLength = adapter1.getImageArrayLength();
        setAutoViewPagerInitialPosition(imagesLength); // 초기 위치 설정

        // 인디케이터 초기화
        CircleIndicator circleIndicator = binding.slideIndicator;
        Log.println(Log.INFO, "HomeFragment", "인디케이터 초기화 발생 길이: " + imagesLength);

        // 기존 인디케이터 점들을 삭제
        circleIndicator.clearDotPanel();

        binding.slideIndicator.removeAllViews();

        // 실제 이미지 수에 맞게 점 개수를 조정
        circleIndicator.createDotPanel(imagesLength, R.drawable.indicator_unselected, R.drawable.indicator_selected, 0);

        // ViewPager2 페이지 변경 콜백 등록
        vpAutoSlide.registerOnPageChangeCallback(new ViewPager2PageChangeCallback(circleIndicator, imagesLength));
    }

    private void setEventAd(List<String> ImageUrls) {
        ViewPager2 vpEvent = binding.vpEvent;
        SlideImageAdapter adapter2 = new SlideImageAdapter(getContext(), ImageUrls);
        vpEvent.setAdapter(adapter2);
    }


    private void setAutoViewPagerInitialPosition(int imagesLength) {
        // ViewPager2가 가상의 무한 리스트 중간에서 시작하도록 초기 위치를 계산
        int initialPosition = Integer.MAX_VALUE / 2;
        // initialPosition을 이미지 길이의 배수로 조정하여 시작점에서 시작하도록 보장
        if (initialPosition % imagesLength != 0) {
            initialPosition -= initialPosition % imagesLength;
        }
        vpAutoSlide.setCurrentItem(initialPosition, false);
    }


    private void setupAutoSlide() {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int imagesLength = ((SlideImageAdapter) vpAutoSlide.getAdapter()).getImageArrayLength();
                // 실제 이미지 길이를 기준으로 다음 아이템 위치 계산
                currentItem = (currentItem + 1) % imagesLength; // 여기서 imagesLength는 실제 이미지 배열의 길이
                // 가상 무한 스크롤을 위한 다음 위치 계산
                int nextItemPosition = calculateNextItemPosition(imagesLength);
                vpAutoSlide.setCurrentItem(nextItemPosition, true);
                sliderHandler.postDelayed(this, 4000); // 다음 이미지로 전환하기 전 3초 대기
            }
        };
        sliderHandler.postDelayed(runnable, 4000); // 앱 시작 시 자동 슬라이딩 시작
    }

    private int calculateNextItemPosition(int imagesLength) {
        return ((Integer.MAX_VALUE / 2) - ((Integer.MAX_VALUE / 2) % imagesLength)) + currentItem;
    }

    private void fetchAndDisplayCommonRecyclerView() {
        NetworkManager.fetchTopToonItems(new Callback<TopToonItems>() {
            @Override
            public void onResponse(@NonNull Call<TopToonItems> call, @NonNull Response<TopToonItems> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.println(Log.INFO, "HomeFragment", "Common 데이터를 받아옴");
                    List<TopToonItems.Webtoon> allWebtoons = response.body().getWebtoons();
                    displayFreeWait(findWebtoonById(response.body().getWaitFree(), allWebtoons));
                    displayOneCoin(findWebtoonById(response.body().getOneCoin(), allWebtoons));

                }
            }

            @Override
            public void onFailure(@NonNull Call<TopToonItems> call, @NonNull Throwable t) {
                Log.println(Log.ERROR, "HomeFragment", "Common 데이터를 받아오는 데 실패함");
            }
        });
    }

    private List<TopToonItems.Webtoon> findWebtoonById(List<Integer> idList, List<TopToonItems.Webtoon> allWebtoons) {
        List<TopToonItems.Webtoon> selectedWebtoons = new ArrayList<>();
        for (Integer id : idList) {
            for (TopToonItems.Webtoon webtoon : allWebtoons) {
                if (webtoon.getId() == id) {
                    selectedWebtoons.add(webtoon);
                    break;
                }
            }
        }
        return selectedWebtoons;
    }

    private void displayFreeWait(List<TopToonItems.Webtoon> waitFreeList) {
        if (waitFreeList != null) {
            List<CommonContentItem> items = new ArrayList<>();
            for (TopToonItems.Webtoon item : waitFreeList) {
                items.add(new CommonContentItem(
                        item.getImageUrl(),
                        item.getTitle(),
                        item.getAuthor()
                ));
            }
            adapterFreeWait.submitList(items);
        }
    }

    private void displayOneCoin(List<TopToonItems.Webtoon> oneCoinList) {
        if (oneCoinList != null) {
            List<CommonContentItem> items = new ArrayList<>();
            for (TopToonItems.Webtoon item : oneCoinList) {
                items.add(new CommonContentItem(
                        item.getImageUrl(),
                        item.getTitle(),
                        item.getAuthor()
                ));
            }
            adapterOneCoin.submitList(items);
        }
    }


    private void initializeRecyclerViews() {
        adapterFreeWait = new CommonRvAdapter();
        setupRecyclerView(binding.rvWaitFree, adapterFreeWait);

        adapterOneCoin = new CommonRvAdapter();
        setupRecyclerView(binding.rvOneCoin, adapterOneCoin);

        adapterCustomKeyword = new CommonRvAdapter();
        setupRecyclerView(binding.rvCustomKeyword, adapterCustomKeyword);

        adapterRecommendGenre = new CommonRvAdapter();
        setupRecyclerView(binding.rvRecommendGenre, adapterRecommendGenre);
    }

    private void setupRecyclerView(RecyclerView recyclerView, CommonRvAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    private void setupTabLayoutWithViewPager() {
        binding.categoryViewPager.setAdapter(new TabManager(this));
        // TabLayout과 ViewPager2 연동
        new TabLayoutMediator(binding.tabLayout, binding.categoryViewPager, this::setupTabTitles).attach();
    }

    private void setupTabTitles(TabLayout.Tab tab, int position) {
        String[] tabTitles = getResources().getStringArray(R.array.tab_title);
        if (position < tabTitles.length) {
            tab.setText(tabTitles[position]);
        }
    }

    private void setupTagMenu() {
        TagMenuRecyclerViewWithAdapter(binding.rvCustomKeywordMenu, createCustomKeywordMenu(), 0);
        TagMenuRecyclerViewWithAdapter(binding.rvRecommendGenreMenu, createRecommendGenre(), 1);
        initializeMapping();
    }

    private void TagMenuRecyclerViewWithAdapter(
            RecyclerView recyclerView,
            List<TagMenuItem> menuList,
            int type) {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));

        // 리스너 인터페이스 구현 변경
        OnTagSelectedListener listener = new OnTagSelectedListener() {
            @Override
            public void onTagSelected(String tag) {
                // 선택된 태그에 대한 데이터 로드
                fetchWebtoonsForTag(tag);
            }
        };

        TagMenuRvAdapter adapter = new TagMenuRvAdapter(type, listener);
        recyclerView.setAdapter(adapter);
        adapter.submitList(menuList);

        adapter.setSelectedItem(0);
    }

    private void fetchWebtoonsForTag(String tag) {
        NetworkManager.fetchTopToonItems(new Callback<TopToonItems>() {
            @Override
            public void onResponse(@NonNull Call<TopToonItems> call, @NonNull Response<TopToonItems> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TopToonItems items = response.body();
                    List<Integer> webtoonIds;
                    List<TopToonItems.Webtoon> webtoons;

                    if (customKeywordTagToJsonKey.containsKey(tag)) {
                        webtoonIds = getWebtoonIdsForTag(items, tag);
                        webtoons = filterWebtoonsByIds(items.getWebtoons(), webtoonIds);
                        updateWebtoonRecyclerView(webtoons, adapterCustomKeyword);
                    } else if (recommendGenreTagToJsonKey.containsKey(tag)) {
                        webtoonIds = getWebtoonIdsForGenre(items, tag);
                        webtoons = filterWebtoonsByIds(items.getWebtoons(), webtoonIds);
                        updateWebtoonRecyclerView(webtoons, adapterRecommendGenre);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TopToonItems> call, @NonNull Throwable t) {
                // 네트워크 에러 처리
            }
        });
    }

    private List<TopToonItems.Webtoon> filterWebtoonsByIds(List<TopToonItems.Webtoon> webtoons, List<Integer> ids) {
        return webtoons.stream().filter(w -> ids.contains(w.getId())).collect(Collectors.toList());
    }

    private void updateWebtoonRecyclerView(List<TopToonItems.Webtoon> webtoons, CommonRvAdapter adapter) {
        if (webtoons != null) {
            List<CommonContentItem> items = new ArrayList<>();
            for (TopToonItems.Webtoon webtoon : webtoons) {
                items.add(new CommonContentItem(webtoon.getImageUrl(), webtoon.getTitle(), webtoon.getAuthor()));
            }
            adapter.submitList(items);
        }
    }


    private void initializeMapping() {
        customKeywordTagToJsonKey = new HashMap<>();
        recommendGenreTagToJsonKey = new HashMap<>();
        String[] customKeywordTags = getResources().getStringArray(R.array.custom_keyword);
        String[] customKeywordJsonKeys = getResources().getStringArray(R.array.custom_keyword_json);
        String[] recommendGenreTags = getResources().getStringArray(R.array.recommend_genre);
        String[] recommendGenreJsonKeys = getResources().getStringArray(R.array.recommend_genre_json);

        for (int i = 0; i < customKeywordTags.length; i++) {
            customKeywordTagToJsonKey.put(customKeywordTags[i], customKeywordJsonKeys[i]);
        }
        for (int i = 0; i < recommendGenreTags.length; i++) {
            recommendGenreTagToJsonKey.put(recommendGenreTags[i], recommendGenreJsonKeys[i]);
        }
    }

    private List<Integer> getWebtoonIdsForTag(TopToonItems items, String tag) {
        String jsonKey = customKeywordTagToJsonKey.get(tag);
        if (jsonKey != null) {
            switch (jsonKey) {
                case "popularWorks":
                    return items.getCustomKeyword().getPopularWorks();
                case "topToonExclusive":
                    return items.getCustomKeyword().getToptoonExclusive();
                case "dailyFree":
                    return items.getCustomKeyword().getDailyFree();
                case "completelyFree":
                    return items.getCustomKeyword().getCompletelyFree();
                case "hotNewWorks":
                    return items.getCustomKeyword().getHotNewWorks();
                case "remakes":
                    return items.getCustomKeyword().getRemakes();
                case "millionViews":
                    return items.getCustomKeyword().getMillionViews();
                case "bingeWatching":
                    return items.getCustomKeyword().getBingeWatching();
                default:
                    return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }

    private List<Integer> getWebtoonIdsForGenre(TopToonItems items, String tag) {
        String jsonKey = recommendGenreTagToJsonKey.get(tag);
        if (jsonKey != null) {
            switch (jsonKey) {
                case "romance":
                    return items.getRecommendGenre().getRomance();
                case "drama":
                    return items.getRecommendGenre().getDrama();
                case "schoolAction":
                    return items.getRecommendGenre().getSchoolAction();
                case "omnibus":
                    return items.getRecommendGenre().getOmnibus();
                case "fantasySF":
                    return items.getRecommendGenre().getFantasySF();
                case "horrorThriller":
                    return items.getRecommendGenre().getHorrorThriller();
                case "comedy":
                    return items.getRecommendGenre().getComedy();
                case "martialArts":
                    return items.getRecommendGenre().getMartialArts();
            }
        }
        return new ArrayList<>();
    }


    @NonNull
    private List<TagMenuItem> createCustomKeywordMenu() {
        List<TagMenuItem> menuList = new ArrayList<>();
        for (String item : getResources().getStringArray(R.array.custom_keyword)) {
            menuList.add(new TagMenuItem(item));
        }
        return menuList;
    }

    @NonNull
    private List<TagMenuItem> createRecommendGenre() {
        List<TagMenuItem> menuList = new ArrayList<>();
        for (String item : getResources().getStringArray(R.array.recommend_genre)) {
            menuList.add(new TagMenuItem(item));
        }
        return menuList;
    }


    private void setFreeAd() {
        NetworkManager.fetchTopToonItems(new Callback<TopToonItems>() {
            @Override
            public void onResponse(@NonNull Call<TopToonItems> call, @NonNull Response<TopToonItems> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String freeAdUrl = response.body()
                            .getFreeAd();
                    Log.println(Log.INFO, "HomeFragment", "freeAdUrl: " + freeAdUrl);
                    if (freeAdUrl != null && !freeAdUrl.isEmpty()) {
                        Glide.with(HomeFragment.this)
                                .load(freeAdUrl)
                                .into(binding.ivFreeAd);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TopToonItems> call, @NonNull Throwable t) {
                Log.println(Log.ERROR, "HomeFragment", "onFailure: " + t.getMessage());
            }
        });
    }

    private void setSectionAd() {
        NetworkManager.fetchTopToonItems(new Callback<TopToonItems>() {
            @Override
            public void onResponse(@NonNull Call<TopToonItems> call, @NonNull Response<TopToonItems> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String sectionAdUrl = response.body()
                            .getSectionAd();
                    Log.println(Log.INFO, "HomeFragment", "sectionAdUrl: " + sectionAdUrl);
                    if (sectionAdUrl != null && !sectionAdUrl.isEmpty()) {
                        Glide.with(HomeFragment.this)
                                .load(sectionAdUrl)
                                .into(binding.ivSectionAd);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TopToonItems> call, @NonNull Throwable t) {
                Log.println(Log.ERROR, "HomeFragment", "onFailure: " + t.getMessage());
            }
        });
    }

    // ViewPager2 페이지 변경을 처리하는 내부 클래스
    private class ViewPager2PageChangeCallback extends ViewPager2.OnPageChangeCallback {
        private final CircleIndicator circleIndicator;
        private final int imagesLength;

        public ViewPager2PageChangeCallback(CircleIndicator circleIndicator, int imagesLength) {
            this.circleIndicator = circleIndicator;
            this.imagesLength = imagesLength;
        }

        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
            if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                // 사용자가 슬라이딩을 시작하면 기존에 예약된 자동 슬라이딩 작업을 모두 취소
                sliderHandler.removeCallbacksAndMessages(null);
            } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
                // 슬라이딩이 끝나고 정지 상태가 되면, 다시 자동 슬라이딩을 시작하기 전에 기존 작업을 취소
                sliderHandler.removeCallbacksAndMessages(null);
                // 그 다음, 자동 슬라이딩 작업을 새로 예약
                sliderHandler.postDelayed(this::autoSlide, 3000);
            }
        }

        private void autoSlide() {
            int imagesLength = ((SlideImageAdapter) vpAutoSlide.getAdapter()).getImageArrayLength();
            currentItem = (currentItem + 1) % imagesLength;
            int nextItemPosition = calculateNextItemPosition(imagesLength);
            vpAutoSlide.setCurrentItem(nextItemPosition, true);
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            int realPosition = position % imagesLength;
            circleIndicator.selectDot(realPosition);
            currentItem = realPosition;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 메모리 누수를 방지하기 위해 sliderHandler 콜백 취소
        sliderHandler.removeCallbacksAndMessages(null);
        binding = null;
    }
}
