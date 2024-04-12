package com.example.toptoon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toptoon.databinding.MainMenuRvRowBinding;

public class MainMenuRvAdapter extends ListAdapter<MainMenuItem, MainMenuRvAdapter.MainMenuViewHolder> {

    protected MainMenuRvAdapter() {
        super(new DiffUtil.ItemCallback<MainMenuItem>() {
            @Override
            public boolean areItemsTheSame(@NonNull MainMenuItem oldItem, @NonNull MainMenuItem newItem) {
                // 여기서는 단순히 타이틀로 비교
                return oldItem.getTitle().equals(newItem.getTitle());
            }

            @Override
            public boolean areContentsTheSame(@NonNull MainMenuItem oldItem, @NonNull MainMenuItem newItem) {
                return oldItem.equals(newItem);
            }
        });
    }

    @NonNull
    @Override
    public MainMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MainMenuRvRowBinding binding = MainMenuRvRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MainMenuViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MainMenuRvAdapter.MainMenuViewHolder holder, int position) {
        MainMenuItem menu = getItem(position);
        holder.binding.tvItem.setText(menu.getTitle());
    }

    static class MainMenuViewHolder extends RecyclerView.ViewHolder {
        private final MainMenuRvRowBinding binding;

        public MainMenuViewHolder(@NonNull  MainMenuRvRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
