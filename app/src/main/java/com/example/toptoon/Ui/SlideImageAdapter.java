package com.example.toptoon.Ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toptoon.databinding.FragmentHomeBinding;
import com.example.toptoon.databinding.SlideImageRowBinding;

import java.util.List;

public class SlideImageAdapter extends RecyclerView.Adapter<SlideImageAdapter.SlideImageViewHolder> {


    private final List<String> imageUrls;
    private Context context;
    private FragmentHomeBinding binding;

    public SlideImageAdapter(Context context, List<String> imageUrls){
        this.context = context;
        this.imageUrls = imageUrls;
    }

    // 이미지 배열의 길이를 반환하는 메서드 추가
    public int getImageArrayLength() {
        return imageUrls.size();
    }

    @NonNull
    @Override
    public SlideImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SlideImageRowBinding binding = SlideImageRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SlideImageViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull SlideImageViewHolder holder, int position) {
        int realPosition = position % imageUrls.size(); // 실제 이미지 배열의 위치를 계산
        Glide.with(context).load(imageUrls.get(realPosition)).into(holder.binding.slideImageRow);
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

