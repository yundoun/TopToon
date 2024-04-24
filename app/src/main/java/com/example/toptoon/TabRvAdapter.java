package com.example.toptoon;

import android.annotation.SuppressLint;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toptoon.databinding.TabRvRowBinding;

import java.util.List;

public class TabRvAdapter extends ListAdapter<TabContentItem, TabRvAdapter.TabRvViewHolder> {

    public TabRvAdapter() {
        super(new DiffUtil.ItemCallback<TabContentItem>() {
            @Override
            public boolean areItemsTheSame(@NonNull TabContentItem oldItem, @NonNull TabContentItem newItem) {
                // 각 항목을 고유하게 식별할 수 있는 속성을 비교 (예: imageUrl)
                return oldItem.getImageUrl().equals(newItem.getImageUrl());
            }

            @Override
            public boolean areContentsTheSame(@NonNull TabContentItem oldItem, @NonNull TabContentItem newItem) {
                // 항목의 상세 내용이 같은지 비교 (equals 메소드 사용 권장)
                return oldItem.equals(newItem);
            }
        });
    }

    @NonNull
    @Override
    public TabRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        TabRvRowBinding binding = TabRvRowBinding.inflate(layoutInflater, parent, false);
        return new TabRvViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TabRvViewHolder holder, int position) {
        TabContentItem tabContentItem = getItem(position);
        // rank 값을 설정. position은 0부터 시작하므로, rank를 표시할 때는 +1을 해줍니다.
        String rank = String.valueOf(position + 1);
        holder.bind(tabContentItem, rank);
    }

    public static class TabRvViewHolder extends RecyclerView.ViewHolder {
        private final TabRvRowBinding binding;

        public TabRvViewHolder(TabRvRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(TabContentItem tabContentItem, String rank){
            Glide.with(itemView.getContext())
                    .load(tabContentItem.getImageUrl()) // 이미지 URL 사용
                    .into(binding.ivTabWebtoon);

            binding.tvRank.setText(rank); // Rank 값을 설정
            binding.tvEpisode.setText(tabContentItem.getLatestEpisode());
            binding.tvViews.setText(tabContentItem.getViews());
            binding.tvTitle.setText(tabContentItem.getTitle());

            // 가시성 설정
            binding.ivHits.setVisibility(tabContentItem.isWaitFree() ? View.VISIBLE : View.GONE );
            binding.ivNew.setVisibility(tabContentItem.isNew() ? View.VISIBLE : View.GONE );
            binding.ivDiscounted.setVisibility(tabContentItem.isDiscounted() ? View.VISIBLE : View.GONE );
            binding.ivUpdate.setVisibility(tabContentItem.isRecentlyUpdated() ? View.VISIBLE : View.GONE );
            binding.ivExclusive.setVisibility(tabContentItem.isExclusive() ? View.VISIBLE : View.GONE );
        }
    }
}
