package com.example.toptoon;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toptoon.databinding.CommonRvRowBinding;

public class CommonRvAdapter extends ListAdapter<CommonContentItem, CommonRvAdapter.CommonViewHolder> {


    protected CommonRvAdapter() {
        super(new DiffUtil.ItemCallback<CommonContentItem>() {
            @Override
            public boolean areItemsTheSame(@NonNull CommonContentItem oldItem, @NonNull CommonContentItem newItem) {
                return oldItem.getImageUrl().equals(newItem.getImageUrl());
            }

            @Override
            public boolean areContentsTheSame(@NonNull CommonContentItem oldItem, @NonNull CommonContentItem newItem) {
                return oldItem.equals(newItem);
            }
        });
    }


    @NonNull
    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        CommonRvRowBinding binding = CommonRvRowBinding.inflate(layoutInflater, parent, false);
        return new CommonViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {
        CommonContentItem commonContentItem = getItem(position);
        holder.bind(commonContentItem);
    }

    public static class CommonViewHolder extends RecyclerView.ViewHolder {
        private final CommonRvRowBinding binding;

        public CommonViewHolder(@NonNull CommonRvRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CommonContentItem commonContentItem) {
            Glide.with(itemView.getContext())
                    .load(commonContentItem.getImageUrl())
                    .into(binding.ivImage);

            binding.tvTitle.setText(commonContentItem.getTitle());
            binding.tvAuthor.setText(commonContentItem.getAuthor());
        }
    }
}
