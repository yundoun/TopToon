package com.example.toptoon.Ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toptoon.DataModel.BaseContentItem;
import com.example.toptoon.databinding.TabRvRowBinding;

public class TabRvAdapter extends ListAdapter<BaseContentItem, TabRvAdapter.TabRvViewHolder> {

    public TabRvAdapter() {
        super(new DiffUtil.ItemCallback<BaseContentItem>() {
            @Override
            public boolean areItemsTheSame(@NonNull BaseContentItem oldItem, @NonNull BaseContentItem newItem) {
                // 각 항목을 고유하게 식별할 수 있는 속성을 비교 (예: imageUrl)
                return oldItem.getImageUrl().equals(newItem.getImageUrl());
            }

            @Override
            public boolean areContentsTheSame(@NonNull BaseContentItem oldItem, @NonNull BaseContentItem newItem) {
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
        BaseContentItem baseContentItem = getItem(position);
        // rank 값을 설정. position은 0부터 시작하므로, rank를 표시할 때는 +1을 해줍니다.
        String rank = String.valueOf(position + 1);
        holder.bind(baseContentItem, rank);
    }

    public static class TabRvViewHolder extends RecyclerView.ViewHolder {
        private final TabRvRowBinding binding;

        public TabRvViewHolder(TabRvRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(BaseContentItem baseContentItem, String rank){
            Glide.with(itemView.getContext())
                    .load(baseContentItem.getImageUrl()) // 이미지 URL 사용
                    .into(binding.ivTabWebtoon);

            binding.tvRank.setText(rank); // Rank 값을 설정
            binding.tvEpisode.setText(baseContentItem.getLatestEpisode());
            binding.tvViews.setText(baseContentItem.getViews());
            binding.tvTitle.setText(baseContentItem.getTitle());

            // 가시성 설정
            binding.ivHits.setVisibility(baseContentItem.isWaitFree() ? View.VISIBLE : View.GONE );
            binding.ivNew.setVisibility(baseContentItem.isNew() ? View.VISIBLE : View.GONE );
            binding.ivDiscounted.setVisibility(baseContentItem.isDiscounted() ? View.VISIBLE : View.GONE );
            binding.ivUpdate.setVisibility(baseContentItem.isRecentlyUpdated() ? View.VISIBLE : View.GONE );
            binding.ivExclusive.setVisibility(baseContentItem.isExclusive() ? View.VISIBLE : View.GONE );
        }
    }
}
