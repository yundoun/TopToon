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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DrawerRvAdapter extends ListAdapter<DrawerItem, DrawerRvAdapter.DrawerRvViewHolder>{

private List<Integer> images = new ArrayList<>();

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

        images = Arrays.asList(
                R.drawable.drawer_library, R.drawable.drawer_attendance, R.drawable.drawer_freecoin,
                R.drawable.drawer_event, R.drawable.drawer_notice, R.drawable.drawer_faq, R.drawable.drawer_cs
        );

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

        if (position < images.size()) {
            holder.binding.ivDrawerIcon.setImageResource(images.get(position));
        } else {
            holder.binding.ivDrawerIcon.setImageResource(0); // 기본 이미지
        }

        if (position == 7){
            holder.binding.ivArrow.setVisibility(View.GONE);
        }
    }

    public static class DrawerRvViewHolder extends RecyclerView.ViewHolder {

        private DrawerRvRowBinding binding;

        public DrawerRvViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DrawerRvRowBinding.bind(itemView);
        }
    }
}

