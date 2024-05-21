package com.example.toptoon.Ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.toptoon.DataModel.SlideItem;
import com.example.toptoon.WebViewActivity;
import com.example.toptoon.databinding.SlideImageRowBinding;

import java.util.List;

public class SlideImageAdapter extends RecyclerView.Adapter<SlideImageAdapter.SlideImageViewHolder> {


    private final List<SlideItem> slideItems;
    private final Context context;

    public SlideImageAdapter(Context context, List<SlideItem> slideItems) {
        this.context = context;
        this.slideItems = slideItems;
    }

    // 이미지 배열의 길이를 반환하는 메서드 추가
    public int getImageArrayLength() {
        return slideItems.size();
    }

    @NonNull
    @Override
    public SlideImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SlideImageRowBinding binding = SlideImageRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SlideImageViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull SlideImageViewHolder holder, int position) {
        int realPosition = position % slideItems.size(); // 실제 이미지 배열의 위치를 계산
        SlideItem slideItem = slideItems.get(realPosition);
        Glide.with(context)
                .load(slideItem.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder.binding.slideImageRow);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra("URL", slideItem.getLinkUrl()); // 웹뷰로 전송할 URL
            context.startActivity(intent);
        });

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

