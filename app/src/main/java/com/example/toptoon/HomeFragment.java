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

        slideViewPager = binding.slideViewPager;
        slideViewPager.setAdapter(new SlideImageAdapter(getContext()));

        // 자동 슬라이딩 구현
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (currentItem >= slideViewPager.getAdapter().getItemCount()) {
                    currentItem = 0;
                }
                slideViewPager.setCurrentItem(currentItem++, true);
                sliderHandler.postDelayed(this, 3000); // 3초 후 다음 이미지로 전환
            }
        };
        sliderHandler.postDelayed(runnable, 3000);

        slideViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // 현재 페이지 인덱스 업데이트
                currentItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == ViewPager2.SCROLL_STATE_IDLE) { // 스크롤이 멈췄을 때
                    int itemCount = slideViewPager.getAdapter().getItemCount(); // 전체 아이템 개수를 가져옵니다.
                    if (currentItem == 0) {
                        // 첫 번째 페이지에서 왼쪽으로 스와이프하면 마지막 페이지로 이동
                        slideViewPager.setCurrentItem(itemCount  - 1, false);
                    } else if (currentItem == itemCount - 1) {
                        // 마지막 페이지에서 오른쪽으로 스와이프하면 첫 번째 페이지로 이동
                        slideViewPager.setCurrentItem(0, false);
                    }
                }
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        sliderHandler.removeCallbacksAndMessages(null); // 리소스 정리
        binding = null; // 메모리 누수 방지
    }
}


