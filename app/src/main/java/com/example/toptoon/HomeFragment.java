package com.example.toptoon;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.toptoon.databinding.FragmentHomeBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private ViewPager2 slideViewPager;
    private Handler sliderHandler = new Handler();
    private int currentItem = 0;
    private FragmentHomeBinding binding;

    private RecyclerView rvCommon1, rvCommon2;
    private CommonRvAdapter commonRvAdapter1, commonRvAdapter2;
    private ArrayList<CommonContentItem> commonContentItems1, commonContentItems2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        initializeSlider(); // 슬라이더 초기화
        setupAutoSlide();   // 자동 슬라이딩 설정

        // 데이터 초기화
        commonContentItems1 = new ArrayList<>();
        commonContentItems1.add(new CommonContentItem(R.drawable.common_1, "계약 남편에게 끌리...", "장미 스튜디오&열문"));
        commonContentItems1.add(new CommonContentItem(R.drawable.common_2, "출구 없는 사랑", "YY&Jiman"));

        commonContentItems2 = new ArrayList<>();
        commonContentItems2.add(new CommonContentItem(R.drawable.common_1, "Text 3-1", "Text 3-2"));
        commonContentItems2.add(new CommonContentItem(R.drawable.common_2, "Text 4-1", "Text 4-2"));

        binding.rvCommon1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        commonRvAdapter1 = new CommonRvAdapter(commonContentItems1);
        binding.rvCommon1.setAdapter(commonRvAdapter1);

        binding.rvCommon2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        commonRvAdapter2 = new CommonRvAdapter(commonContentItems2);
        binding.rvCommon2.setAdapter(commonRvAdapter2);


        // 어댑터 설정
        binding.categoryViewPager.setAdapter(new TabAdapter(this));

        // TabLayout과 ViewPager2 연동
        new TabLayoutMediator(binding.tabLayout, binding.categoryViewPager,
                (tab, position) -> {
                    // 여기서 탭 제목 설정
                    switch (position) {
                        case 0:
                            tab.setText("실시간");
                            break;
                        case 1:
                            tab.setText("신작");
                            break;
                        case 2:
                            tab.setText("할인");
                            break;
                        case 3:
                            tab.setText("내가보던");
                            break;
                    }
                }).attach();


        return binding.getRoot();
    }

    private void initializeSlider() {
        SlideImageAdapter adapter = new SlideImageAdapter(getContext());
        slideViewPager = binding.slideViewPager;
        slideViewPager.setAdapter(adapter);

        int imagesLength = adapter.getImageArrayLength();
        setInitialPosition(imagesLength); // 초기 위치 설정

        // 인디케이터 초기화
        CircleIndicator circleIndicator = binding.slideIndicator;
        // 실제 이미지 수에 맞게 점 개수를 조정
        circleIndicator.createDotPanel(imagesLength, R.drawable.indicator_unselected, R.drawable.indicator_selected, 0);

        // ViewPager2 페이지 변경 콜백 등록
        slideViewPager.registerOnPageChangeCallback(new ViewPager2PageChangeCallback(circleIndicator, imagesLength));
    }

    // ViewPager2 초기 위치 설정
    private void setInitialPosition(int imagesLength) {
        // ViewPager2가 가상의 무한 리스트 중간에서 시작하도록 초기 위치를 계산
        int initialPosition = Integer.MAX_VALUE / 2;
        // initialPosition을 이미지 길이의 배수로 조정하여 시작점에서 시작하도록 보장
        if (initialPosition % imagesLength != 0) {
            initialPosition -= initialPosition % imagesLength;
        }
        slideViewPager.setCurrentItem(initialPosition, false);
    }

    private void setupAutoSlide() {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int imagesLength = ((SlideImageAdapter) slideViewPager.getAdapter()).getImageArrayLength();
                // 실제 이미지 길이를 기준으로 다음 아이템 위치 계산
                currentItem = (currentItem + 1) % imagesLength; // 여기서 imagesLength는 실제 이미지 배열의 길이
                // 가상 무한 스크롤을 위한 다음 위치 계산
                int nextItemPosition = calculateNextItemPosition(imagesLength);
                slideViewPager.setCurrentItem(nextItemPosition, true);
                sliderHandler.postDelayed(this, 3000); // 다음 이미지로 전환하기 전 3초 대기
            }
        };
        sliderHandler.postDelayed(runnable, 3000); // 앱 시작 시 자동 슬라이딩 시작
    }

    private int calculateNextItemPosition(int imagesLength) {
        return ((Integer.MAX_VALUE / 2) - ((Integer.MAX_VALUE / 2) % imagesLength)) + currentItem;
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
            int imagesLength = ((SlideImageAdapter) slideViewPager.getAdapter()).getImageArrayLength();
            currentItem = (currentItem + 1) % imagesLength;
            int nextItemPosition = calculateNextItemPosition(imagesLength);
            slideViewPager.setCurrentItem(nextItemPosition, true);
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
