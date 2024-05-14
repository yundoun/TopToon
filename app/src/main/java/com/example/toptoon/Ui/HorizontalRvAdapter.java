package com.example.toptoon.Ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toptoon.DataModel.HorizontalContentItem;
import com.example.toptoon.databinding.HorizontalRvRowBinding;

public class HorizontalRvAdapter extends ListAdapter<HorizontalContentItem, HorizontalRvAdapter.CommonViewHolder> {


    public HorizontalRvAdapter() {
        super(new DiffUtil.ItemCallback<HorizontalContentItem>() {
            @Override
            public boolean areItemsTheSame(@NonNull HorizontalContentItem oldItem, @NonNull HorizontalContentItem newItem) {
                return oldItem.getImageUrl().equals(newItem.getImageUrl());
            }

            @Override
            public boolean areContentsTheSame(@NonNull HorizontalContentItem oldItem, @NonNull HorizontalContentItem newItem) {
                return oldItem.equals(newItem);
            }
        });
    }


    @NonNull
    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        HorizontalRvRowBinding binding = HorizontalRvRowBinding.inflate(layoutInflater, parent, false);
        return new CommonViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {
        HorizontalContentItem horizontalContentItem = getItem(position);
        holder.bind(horizontalContentItem);
    }

    public static class CommonViewHolder extends RecyclerView.ViewHolder {
        private final HorizontalRvRowBinding binding;

        public CommonViewHolder(@NonNull HorizontalRvRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(HorizontalContentItem horizontalContentItem) {
            Glide.with(itemView.getContext())
                    .load(horizontalContentItem.getImageUrl())
                    .into(binding.ivImage);

            binding.tvTitle.setText(horizontalContentItem.getTitle());
            binding.tvAuthor.setText(horizontalContentItem.getAuthor());
        }
    }
}