package com.example.toptoon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toptoon.databinding.SlideImageRowBinding;

public class SlideImageAdapter extends RecyclerView.Adapter<SlideImageAdapter.SlideImageViewHolder> {

    private Context context;
    private int[] images = new int[]{R.drawable.slide_add_1, R.drawable.slide_add_2, R.drawable.slide_add_3, R.drawable.slide_add_4, R.drawable.slide_add_5,
            R.drawable.slide_add_6, R.drawable.slide_add_7};

    SlideImageAdapter(Context context) {
        this.context = context;
    }

    // 이미지 배열의 길이를 반환하는 메서드 추가
    public int getImageArrayLength() {
        return images.length;
    }

    @NonNull
    @Override
    public SlideImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SlideImageRowBinding binding = SlideImageRowBinding.inflate(LayoutInflater.from(context),parent,false);
        return new SlideImageViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull SlideImageViewHolder holder, int position) {
        int realPosition = position % images.length; // 실제 이미지 배열의 위치를 계산
        holder.binding.slideImageRow.setImageResource(images[realPosition]);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
        // 무한 순환을 위해 아이템 수를 매우 큰 값으로 설정
    }

    static class SlideImageViewHolder extends RecyclerView.ViewHolder {
        SlideImageRowBinding binding;

        SlideImageViewHolder(SlideImageRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

