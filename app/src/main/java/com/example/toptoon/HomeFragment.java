package com.example.toptoon;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class HomeFragment extends Fragment {
    private ViewPager slideViewPager;
    private Handler sliderHandler = new Handler();
    private int currentItem = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        slideViewPager = view.findViewById(R.id.slideViewPager);
        slideViewPager.setAdapter(new SlideImageAdapter(getContext()));

        // 자동 슬라이딩 구현
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (currentItem == slideViewPager.getAdapter().getCount()) {
                    currentItem = 0;
                }
                slideViewPager.setCurrentItem(currentItem++, true);
                sliderHandler.postDelayed(this, 2000); // 3초 후 다음 이미지로 전환
            }
        };
        sliderHandler.postDelayed(runnable, 2000);

        return view;
    }
    // ViewPager 찾아서 초기화 이후 SlideImageAdapter의 인스턴스를 생성해서 Viewpager를 설정함 getContext()메소드는 Fragment내에서 현재 컨텍스트를 가져오기 위해 사용됨

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sliderHandler != null) {
            sliderHandler.removeCallbacksAndMessages(null); // Fragment가 종료될 때 핸들러 콜백 제거
        }
    }
}

