package com.example.toptoon;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.toptoon.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private ViewPager2 slideViewPager;
    private Handler sliderHandler = new Handler();
    private int currentItem = 0;
    private FragmentHomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        SlideImageAdapter adapter = new SlideImageAdapter(getContext());
        slideViewPager = binding.slideViewPager;
        slideViewPager.setAdapter(adapter);

        int imagesLength = adapter.getImageArrayLength();

        // ViewPager2가 가상의 무한 리스트 중간에서 시작하도록 초기 위치를 계산
        int initialPosition = Integer.MAX_VALUE / 2;
        // initialPosition을 이미지 길이의 배수로 조정하여 시작점에서 시작하도록 보장
        if (initialPosition % imagesLength != 0) {
            initialPosition -= initialPosition % imagesLength;
        }
        slideViewPager.setCurrentItem(initialPosition, false);

        // 인디케이터 초기화
        CircleIndicator circleIndicator = binding.slideIndicator;
        // 실제 이미지 수에 맞게 점 개수를 조정
        circleIndicator.createDotPanel(imagesLength, R.drawable.tab_unselected, R.drawable.tab_selected, 0);

        // ViewPager2에 페이지 변경 콜백 설정하여 인디케이터 업데이트 및 순환 스크롤 처리
        slideViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // 가상 무한 위치를 실제 이미지 인덱스로 변환
                int realPosition = position % imagesLength;
                circleIndicator.selectDot(realPosition);
                currentItem = realPosition; // 현재 항목을 실제 위치로 업데이트
            }
        });

        // 자동 슬라이딩 구현
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int itemCount = slideViewPager.getAdapter().getItemCount();
                // 실제 이미지 길이를 기준으로 다음 아이템 위치 계산
                currentItem = (currentItem + 1) % imagesLength; // 여기서 imagesLength는 실제 이미지 배열의 길이
                // 가상 무한 스크롤을 위한 다음 위치 계산
                int nextItemPosition = ((Integer.MAX_VALUE / 2) - ((Integer.MAX_VALUE / 2) % imagesLength)) + currentItem;
                slideViewPager.setCurrentItem(nextItemPosition, true);
                sliderHandler.postDelayed(this, 3000); // 다음 이미지로 전환하기 전 3초 대기
            }
        };
        // 앱 시작 시 자동 슬라이딩 시작
        sliderHandler.postDelayed(runnable, 3000);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 메모리 누수를 방지하기 위해 sliderHandler 콜백 취소
        sliderHandler.removeCallbacksAndMessages(null);
        binding = null;
    }
}
