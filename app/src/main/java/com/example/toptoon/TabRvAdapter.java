package com.example.toptoon;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toptoon.databinding.TabRvRowBinding;

import java.util.List;

public class TabRvAdapter extends RecyclerView.Adapter<TabRvAdapter.TabRvViewHolder> {

    private List<TabContentItem> tabContentItems;

    public TabRvAdapter(List<TabContentItem> tabContentItems) {
        this.tabContentItems = tabContentItems;
    }

    @NonNull
    @Override
    public TabRvAdapter.TabRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        TabRvRowBinding binding = TabRvRowBinding.inflate(layoutInflater, parent, false);
        return new TabRvViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TabRvViewHolder holder, int position) {
        TabContentItem tabContentItem = tabContentItems.get(position);
        // rank 값을 설정. position은 0부터 시작하므로, rank를 표시할 때는 +1을 해줍니다.
        String rank = String.valueOf(position + 1);
        holder.bind(tabContentItem, rank);
    }

    @Override
    public int getItemCount() {
        return tabContentItems.size();
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
                    .into(binding.ivTabLive);

            binding.tvRank.setText(rank); // Rank 값을 설정
            binding.tvEpisode.setText(tabContentItem.getLatestEpisode());
            binding.tvViews.setText(tabContentItem.getViews());
            binding.tvTitle.setText(tabContentItem.getTitle());
        }
    }
}
