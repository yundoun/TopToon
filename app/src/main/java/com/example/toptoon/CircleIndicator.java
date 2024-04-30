package com.example.toptoon;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.util.AttributeSet;

import com.example.toptoon.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class CircleIndicator extends LinearLayout {

    private final Context mContext;

    private int mDefaultCircle;
    private int mSelectCircle;
    private FragmentHomeBinding binding;

    private List<ImageView> imageDot = new ArrayList<>();

    // 4.5dp를 픽셀 단위로 바꿉니다.
    private final float temp = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 4.5f, getResources().getDisplayMetrics());

    public CircleIndicator(Context context) {
        super(context);
        mContext = context;
    }

    public CircleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    /**
     * 기본 점 생성
     * @param count 점의 갯수
     * @param defaultCircle 기본 점의 이미지
     * @param selectCircle 선택된 점의 이미지
     * @param position 선택된 점의 포지션
     */
    public void createDotPanel(int count, int defaultCircle, int selectCircle, int position) {
        this.removeAllViews();
        mDefaultCircle = defaultCircle;
        mSelectCircle = selectCircle;

        for (int i = 0; i < count; i++) {
            ImageView dot = new ImageView(mContext);
            dot.setPadding((int) temp, 0, (int) temp, 0);
            imageDot.add(dot);

            this.addView(dot);
        }

        // 인덱스 선택
        selectDot(position);
    }

    /**
     * 선택된 점 표시
     */
    public void selectDot(int position) {
        for (int i = 0; i < imageDot.size(); i++) {
            if (i == position) {
                imageDot.get(i).setImageResource(mSelectCircle);
            } else {
                imageDot.get(i).setImageResource(mDefaultCircle);
            }
        }
    }

    public void clearDotPanel() {
        if (binding != null) {
            binding.slideIndicator.removeAllViews();
            this.removeAllViews();
            imageDot.clear();
            Log.d("CircleIndicator", "인디케이터 지우기");
        }
    }
}
