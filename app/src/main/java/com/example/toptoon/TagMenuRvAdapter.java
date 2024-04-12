package com.example.toptoon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toptoon.databinding.TagMenuRvRowBinding;

public class TagMenuRvAdapter extends ListAdapter<TagMenuItem, TagMenuRvAdapter.TagMenuViewHolder> {

        protected TagMenuRvAdapter() {
            super(new DiffUtil.ItemCallback<TagMenuItem>() {
                @Override
                public boolean areItemsTheSame(@NonNull TagMenuItem oldItem, @NonNull TagMenuItem newItem) {
                    // 여기서는 단순히 타이틀로 비교
                    return oldItem.getTitle().equals(newItem.getTitle());
                }

                @Override
                public boolean areContentsTheSame(@NonNull TagMenuItem oldItem, @NonNull TagMenuItem newItem) {
                    return oldItem.equals(newItem);
                }
            });
        }

        @NonNull
        @Override
        public TagMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TagMenuRvRowBinding binding = TagMenuRvRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new TagMenuViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull TagMenuRvAdapter.TagMenuViewHolder holder, int position) {
            TagMenuItem menu = getItem(position);
            holder.binding.tvTagMenu.setText(menu.getTitle());
        }

        static class TagMenuViewHolder extends RecyclerView.ViewHolder {
            private final TagMenuRvRowBinding binding;

            public TagMenuViewHolder(@NonNull TagMenuRvRowBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
}
