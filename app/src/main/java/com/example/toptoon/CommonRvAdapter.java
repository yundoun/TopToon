package com.example.toptoon;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toptoon.databinding.CommonRvRowBinding;

import java.util.List;

public class CommonRvAdapter extends RecyclerView.Adapter<CommonRvAdapter.CommonViewHolder>{
    private final List<CommonContentItem> commonContentItems;

    public CommonRvAdapter(List<CommonContentItem> commonContentItems) {
        this.commonContentItems = commonContentItems;
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
        CommonContentItem commonContentItem = commonContentItems.get(position);
        holder.binding.ivImage.setImageResource(commonContentItem.getImageResourceId());
        holder.binding.tvTitle.setText(commonContentItem.getTitle());
        holder.binding.tvSubTitle.setText(commonContentItem.getSubTitle());
    }

    @Override
    public int getItemCount() {
        return commonContentItems.size();
    }

    public static class CommonViewHolder extends RecyclerView.ViewHolder {
        private final CommonRvRowBinding binding;

        public CommonViewHolder(@NonNull CommonRvRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CommonContentItem commonContentItem){
            binding.ivImage.setImageResource(commonContentItem.getImageResourceId());
            binding.tvTitle.setText(commonContentItem.getTitle());
            binding.tvSubTitle.setText(commonContentItem.getSubTitle());
        }
    }
}
