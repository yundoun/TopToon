package com.example.toptoon.Ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toptoon.DataModel.SerailListItem;
import com.example.toptoon.databinding.MainListRvRowBinding;

public class MainListRvAdapter extends ListAdapter<SerailListItem, MainListRvAdapter.MainListRvViewHolder> {
    public MainListRvAdapter() {
        super(new DiffUtil.ItemCallback<SerailListItem>() {
            @Override
            public boolean areItemsTheSame(@NonNull SerailListItem oldItem, @NonNull SerailListItem newItem) {
                return oldItem.equals(newItem);
            }

            @SuppressLint("DiffUtilEquals")
            @Override
            public boolean areContentsTheSame(@NonNull SerailListItem oldItem, @NonNull SerailListItem newItem) {
                return oldItem.equals(newItem);
            }
        });
    }

    @NonNull
    @Override
    public MainListRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        MainListRvRowBinding binding = MainListRvRowBinding.inflate(layoutInflater, parent, false);
        return new MainListRvViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MainListRvViewHolder holder, int position) {
    }

    public static class MainListRvViewHolder extends RecyclerView.ViewHolder {

    private final MainListRvRowBinding binding;
        public MainListRvViewHolder(MainListRvRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
