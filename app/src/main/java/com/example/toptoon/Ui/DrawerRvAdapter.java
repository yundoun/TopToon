package com.example.toptoon.Ui;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toptoon.DataModel.DrawerItem;
import com.example.toptoon.R;
import com.example.toptoon.databinding.DrawerRvRowBinding;

public class DrawerRvAdapter extends ListAdapter<DrawerItem, DrawerRvAdapter.DrawerRvViewHolder>{


    public DrawerRvAdapter() {
        super(new AsyncDifferConfig.Builder<>(new DiffUtil.ItemCallback<DrawerItem>() {
            @Override
            public boolean areItemsTheSame(@NonNull DrawerItem oldItem, @NonNull DrawerItem newItem) {
                return oldItem.getTitle().equals(newItem.getTitle());
            }

            @Override
            public boolean areContentsTheSame(@NonNull DrawerItem oldItem, @NonNull DrawerItem newItem) {
                return oldItem.getTitle().equals(newItem.getTitle());
            }
        }).build());
    }
    @NonNull
    @Override
    public DrawerRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_rv_row, parent, false);
        return new DrawerRvViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DrawerRvViewHolder holder, int position) {
        DrawerItem item = getItem(position);
        holder.binding.tvDrawer.setText(item.getTitle());
    }

    public static class DrawerRvViewHolder extends RecyclerView.ViewHolder {

        private DrawerRvRowBinding binding;

        public DrawerRvViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DrawerRvRowBinding.bind(itemView);
        }
    }
}

