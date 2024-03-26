package com.example.toptoon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        holder.bind(tabContentItem);
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

        public void bind(TabContentItem tabContentItem){
            binding.ivTabLive.setImageResource(tabContentItem.getImageResourceId());
            binding.ivHits.setImageResource(R.drawable.icon_hits);
            binding.ivLook.setImageResource(R.drawable.icon_look);
            binding.tvRank.setText(tabContentItem.getRank());
            binding.tvEpisode.setText(tabContentItem.getEpisode());
            binding.tvViews.setText(tabContentItem.getViews());
            binding.tvTitle.setText(tabContentItem.getTitle());
        }
    }
}
