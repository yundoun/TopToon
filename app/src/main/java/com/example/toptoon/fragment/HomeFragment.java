package com.example.toptoon.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.toptoon.R;
import com.example.toptoon.WebViewActivity;
import com.example.toptoon.api.NetworkManager;
import com.example.toptoon.databinding.FragmentHomeBinding;
import com.example.toptoon.dto.ApiItems;
import com.example.toptoon.dto.HorizontalContentItem;
import com.example.toptoon.dto.SlideItem;
import com.example.toptoon.dto.TagMenuItem;
import com.example.toptoon.fragment.tap.TabManager;
import com.example.toptoon.ui.CircleIndicator;
import com.example.toptoon.ui.adapters.HorizontalRvAdapter;
import com.example.toptoon.ui.adapters.SlideImageAdapter;
import com.example.toptoon.ui.adapters.TagMenuRvAdapter;
import com.example.toptoon.ui.interfaces.OnTagSelectedListener;
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
    private HorizontalRvAdapter adapterFreeWait;
    private HorizontalRvAdapter adapterOneCoin;
    private HorizontalRvAdapter adapterCustomKeyword;
    private HorizontalRvAdapter adapterRecommendGenre;

    private List<SlideItem> slideItems;
    private List<SlideItem> eventItems;
    private Map<String, String> customKeywordTagToJsonKey, recommendGenreTagToJsonKey;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeComponents();
        adjustViewPagerSettings();
        InitialSettingTagMenu();

        setupViewAllEventListeners();

        setupAdClickListeners();
    }

    private void setupViewAllEventListeners() {
        // 'View All' 버튼 클릭 리스너를 설정합니다. 여러 버튼에 동일한 URL을 사용
        View.OnClickListener viewAllClickListener = v -> {
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra("URL", "https://toptoon.com/hashtag");
            startActivity(intent);
        };

        binding.tvViewAll1.setOnClickListener(viewAllClickListener);
        binding.tvViewAll2.setOnClickListener(viewAllClickListener);
    }

    private void setupAdClickListeners() {
        // 무료 광고 클릭
        binding.ivFreeAd.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra("URL", "https://toptoon.com/comic/ep_list/friendshiptolove_na");
            startActivity(intent);
        });

        // 섹션 광고 클릭
        binding.ivSectionAd.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra("URL", "https://toptoon.com/app/downloadApp");
            startActivity(intent);
        });
    }


    private void onItemClick(HorizontalContentItem item) {
        // 식별자를 사용해 웹뷰로 이동
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        String url = "https://toptoon.com/comic/ep_list/" + item.getSlug();
        intent.putExtra("URL", url);
        startActivity(intent);
    }

    private void adjustViewPagerSettings() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;

        setupViewPager(binding.categoryViewPager, screenWidth / 5, 4, 20, true);  // 페이지 변형 적용
        setupViewPager(binding.vpAutoSlide, screenWidth > 1080 ? screenWidth / 3 : 0, 5, 20, false);  // 페이지 변형 미적용
        setupViewPager(binding.vpEvent, screenWidth > 1080 ? screenWidth / 2 : screenWidth / 5, 2, 20, false);  // 페이지 변형 미적용

        binding.categoryViewPager.post(new Runnable() {
            @Override
            public void run() {
                // 첫 번째 페이지가 로드되면 약간 오른쪽으로 스크롤하여 옆 페이지를 보이게 함
                // 0 번째 탭부터 시작
                binding.categoryViewPager.setCurrentItem(0, false);
                binding.categoryViewPager.scrollTo(binding.categoryViewPager.getScrollX(), 0);
            }
        });

    }

    private void setupViewPager(ViewPager2 viewPager, int padding, int offscreenPageLimit, int pageMargin, boolean applyTransformer) {
        viewPager.setClipToPadding(false); // 스크롤이 발생할 때 패딩 부분을 넘어서 콘텐츠가 보이게 한다.
        viewPager.setClipChildren(false); // 자식 뷰가 Viewpager2의 경계를 벗어나도 그려지게 한다.
        viewPager.setPadding(0, 0, padding, 0);
        viewPager.setPageTransformer(new MarginPageTransformer(pageMargin));
        viewPager.setOffscreenPageLimit(offscreenPageLimit);
        if (applyTransformer) {
            viewPager.setPageTransformer(new ViewPager2.PageTransformer() {
                @Override
                public void transformPage(@NonNull View page, float position) {
                    final float MIN_SCALE = 0.9f;
                    final float MIN_ALPHA = 0.5f;
                    if (position < -1 || position > 1) {
                        page.setAlpha(0);
                    } else {
                        float scale = Math.max(MIN_SCALE, 1 - Math.abs(position));
                        float alpha = Math.max(MIN_ALPHA, 1 - Math.abs(position));
                        page.setScaleX(scale);
                        page.setScaleY(scale);
                        page.setAlpha(alpha);
                        float horzMargin = page.getWidth() * (1 - scale) / 2;
                        page.setTranslationX(position < 0 ? horzMargin - horzMargin / 2 : -horzMargin + horzMargin / 2);
                    }
                }
            });
        }
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
    }

    private void InitialSettingTagMenu() {
        fetchWebtoonsForTag("#인기작품");
        fetchWebtoonsForTag("#로맨스");
    }


    private void fetchSlideAds() {
        NetworkManager.fetchTopToonItems(new Callback<ApiItems>() {
            @Override
            public void onResponse(@NonNull Call<ApiItems> call, @NonNull Response<ApiItems> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiItems items = response.body();
                    Log.println(Log.INFO, "HomeFragment", "데이터를 받아오는 데 성공함");

                    slideItems = new ArrayList<>();
                    for (ApiItems.SlideAd slideAd : items.getSlideAd()) {
                        slideItems.add(new SlideItem(slideAd.getImageUrl(), slideAd.getLinkUrl()));
                    }

                    eventItems = new ArrayList<>();
                    for (ApiItems.Event event : items.getEvent()) {
                        eventItems.add(new SlideItem(event.getImageUrl(), event.getLinkUrl()));
                    }
                    initializeSlider(slideItems);
                    setEventAd(eventItems);
                } else {
                    Log.e("HomeFragment", "응답 실패: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiItems> call, @NonNull Throwable t) {
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

    private void initializeSlider(List<SlideItem> slideItems) {

        if (!isAdded() || binding == null) {
            return;
        }

        vpAutoSlide = binding.vpAutoSlide;
        SlideImageAdapter adapter1 = new SlideImageAdapter(getContext(), slideItems);
        vpAutoSlide.setAdapter(adapter1);

        int imagesLength = adapter1.getImageArrayLength();
        setAutoViewPagerInitialPosition(imagesLength); // 초기 위치 설정

        // 인디케이터 초기화
        CircleIndicator circleIndicator = binding.slideIndicator;
        Log.println(Log.INFO, "HomeFragment", "인디케이터 초기화 발생 길이: " + imagesLength);

        // 실제 이미지 수에 맞게 점 개수를 조정
        circleIndicator.createDotPanel(imagesLength, R.drawable.indicator_unselected, R.drawable.indicator_selected, 0);

        // ViewPager2 페이지 변경 콜백 등록
        vpAutoSlide.registerOnPageChangeCallback(new ViewPager2PageChangeCallback(circleIndicator, imagesLength));
    }

    private void setEventAd(List<SlideItem> eventItems) {
        ViewPager2 vpEvent = binding.vpEvent;
        SlideImageAdapter adapter2 = new SlideImageAdapter(getContext(), eventItems);
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
        NetworkManager.fetchTopToonItems(new Callback<ApiItems>() {
            @Override
            public void onResponse(@NonNull Call<ApiItems> call, @NonNull Response<ApiItems> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.println(Log.INFO, "HomeFragment", "Common 데이터를 받아옴");
                    List<ApiItems.Webtoon> allWebtoons = response.body().getWebtoons();
                    displayFreeWait(findWebtoonById(response.body().getWaitFree(), allWebtoons));
                    displayOneCoin(findWebtoonById(response.body().getOneCoin(), allWebtoons));

                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiItems> call, @NonNull Throwable t) {
                Log.println(Log.ERROR, "HomeFragment", "Common 데이터를 받아오는 데 실패함");
            }
        });
    }

    private List<ApiItems.Webtoon> findWebtoonById(List<Integer> idList, List<ApiItems.Webtoon> allWebtoons) {
        List<ApiItems.Webtoon> selectedWebtoons = new ArrayList<>();
        for (Integer id : idList) {
            for (ApiItems.Webtoon webtoon : allWebtoons) {
                if (webtoon.getId() == id) {
                    selectedWebtoons.add(webtoon);
                    break;
                }
            }
        }
        return selectedWebtoons;
    }

    private void displayFreeWait(List<ApiItems.Webtoon> waitFreeList) {
        if (waitFreeList != null) {
            List<HorizontalContentItem> items = new ArrayList<>();
            for (ApiItems.Webtoon item : waitFreeList) {
                items.add(new HorizontalContentItem(
                        item.getImageUrl(),
                        item.getTitle(),
                        item.getAuthor(),
                        item.getSlug()));
            }
            adapterFreeWait.submitList(items);
        }
    }

    private void displayOneCoin(List<ApiItems.Webtoon> oneCoinList) {
        if (oneCoinList != null) {
            List<HorizontalContentItem> items = new ArrayList<>();
            for (ApiItems.Webtoon item : oneCoinList) {
                items.add(new HorizontalContentItem(
                        item.getImageUrl(),
                        item.getTitle(),
                        item.getAuthor(),
                        item.getSlug()));
            }
            adapterOneCoin.submitList(items);
        }
    }


    private void initializeRecyclerViews() {
        adapterFreeWait = new HorizontalRvAdapter(this::onItemClick);
        setupRecyclerView(binding.rvWaitFree, adapterFreeWait);

        adapterOneCoin = new HorizontalRvAdapter(this::onItemClick);
        setupRecyclerView(binding.rvOneCoin, adapterOneCoin);

        adapterCustomKeyword = new HorizontalRvAdapter(this::onItemClick);
        setupRecyclerView(binding.rvCustomKeyword, adapterCustomKeyword);

        adapterRecommendGenre = new HorizontalRvAdapter(this::onItemClick);
        setupRecyclerView(binding.rvRecommendGenre, adapterRecommendGenre);
    }

    private void setupRecyclerView(RecyclerView recyclerView, HorizontalRvAdapter adapter) {
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
        NetworkManager.fetchTopToonItems(new Callback<ApiItems>() {
            @Override
            public void onResponse(@NonNull Call<ApiItems> call, @NonNull Response<ApiItems> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiItems items = response.body();
                    List<Integer> webtoonIds;
                    List<ApiItems.Webtoon> webtoons;

                    if (customKeywordTagToJsonKey.containsKey(tag)) {
                        webtoonIds = getWebtoonIdsForTag(items, tag, getContext());
                        webtoons = filterWebtoonsByIds(items.getWebtoons(), webtoonIds);
                        updateWebtoonRecyclerView(webtoons, adapterCustomKeyword);
                    } else if (recommendGenreTagToJsonKey.containsKey(tag)) {
                        webtoonIds = getWebtoonIdsForGenre(items, tag, getContext());
                        webtoons = filterWebtoonsByIds(items.getWebtoons(), webtoonIds);
                        updateWebtoonRecyclerView(webtoons, adapterRecommendGenre);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiItems> call, @NonNull Throwable t) {
                // 네트워크 에러 처리
                Log.e("HomeFragment", "Failed to fetch data: " + t.getMessage());
            }
        });
    }

    private List<ApiItems.Webtoon> filterWebtoonsByIds(List<ApiItems.Webtoon> webtoons, List<Integer> ids) {
        return webtoons.stream().filter(w -> ids.contains(w.getId())).collect(Collectors.toList());
    }

    private void updateWebtoonRecyclerView(List<ApiItems.Webtoon> webtoons, HorizontalRvAdapter adapter) {
        if (webtoons != null) {
            List<HorizontalContentItem> items = new ArrayList<>();
            for (ApiItems.Webtoon webtoon : webtoons) {
                items.add(new HorizontalContentItem(webtoon.getImageUrl(), webtoon.getTitle(), webtoon.getAuthor(), webtoon.getSlug()));
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

    private List<Integer> getWebtoonIdsForTag(ApiItems items, String tag, Context context) {
        String jsonKey = customKeywordTagToJsonKey.get(tag);
        if (jsonKey != null) {
            String[] customKeywordJsonArray = context.getResources().getStringArray(R.array.custom_keyword_json);

            if (jsonKey.equals(customKeywordJsonArray[0])) {
                return items.getCustomKeyword().getPopularWorks();
            } else if (jsonKey.equals(customKeywordJsonArray[1])) {
                return items.getCustomKeyword().getToptoonExclusive();
            } else if (jsonKey.equals(customKeywordJsonArray[2])) {
                return items.getCustomKeyword().getDailyFree();
            } else if (jsonKey.equals(customKeywordJsonArray[3])) {
                return items.getCustomKeyword().getCompletelyFree();
            } else if (jsonKey.equals(customKeywordJsonArray[4])) {
                return items.getCustomKeyword().getHotNewWorks();
            } else if (jsonKey.equals(customKeywordJsonArray[5])) {
                return items.getCustomKeyword().getRemakes();
            } else if (jsonKey.equals(customKeywordJsonArray[6])) {
                return items.getCustomKeyword().getMillionViews();
            } else if (jsonKey.equals(customKeywordJsonArray[7])) {
                return items.getCustomKeyword().getBingeWatching();
            }

        }
        return new ArrayList<>();
    }

    private List<Integer> getWebtoonIdsForGenre(ApiItems items, String tag, Context context) {
        String jsonKey = recommendGenreTagToJsonKey.get(tag);
        if (jsonKey != null) {
            String[] recommendGenreJsonArray = context.getResources().getStringArray(R.array.recommend_genre_json);

            if (jsonKey.equals(recommendGenreJsonArray[0])) {
                return items.getRecommendGenre().getRomance();
            } else if (jsonKey.equals(recommendGenreJsonArray[1])) {
                return items.getRecommendGenre().getDrama();
            } else if (jsonKey.equals(recommendGenreJsonArray[2])) {
                return items.getRecommendGenre().getSchoolAction();
            } else if (jsonKey.equals(recommendGenreJsonArray[3])) {
                return items.getRecommendGenre().getOmnibus();
            } else if (jsonKey.equals(recommendGenreJsonArray[4])) {
                return items.getRecommendGenre().getFantasySF();
            } else if (jsonKey.equals(recommendGenreJsonArray[5])) {
                return items.getRecommendGenre().getHorrorThriller();
            } else if (jsonKey.equals(recommendGenreJsonArray[6])) {
                return items.getRecommendGenre().getComedy();
            } else if (jsonKey.equals(recommendGenreJsonArray[7])) {
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
        NetworkManager.fetchTopToonItems(new Callback<ApiItems>() {
            @Override
            public void onResponse(@NonNull Call<ApiItems> call, @NonNull Response<ApiItems> response) {
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
            public void onFailure(@NonNull Call<ApiItems> call, @NonNull Throwable t) {
                Log.println(Log.ERROR, "HomeFragment", "onFailure: " + t.getMessage());
            }
        });
    }

    private void setSectionAd() {
        NetworkManager.fetchTopToonItems(new Callback<ApiItems>() {
            @Override
            public void onResponse(@NonNull Call<ApiItems> call, @NonNull Response<ApiItems> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String sectionAdUrl = response.body()
                            .getSectionAd();
                    Log.println(Log.INFO, "HomeFragment", "sectionAdUrl: " + sectionAdUrl);
                    if (sectionAdUrl != null && !sectionAdUrl.isEmpty()) {
                        Glide.with(HomeFragment.this)
                                .load(sectionAdUrl)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(binding.ivSectionAd);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiItems> call, @NonNull Throwable t) {
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
                sliderHandler.postDelayed(this::autoSlide, 4000);
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
