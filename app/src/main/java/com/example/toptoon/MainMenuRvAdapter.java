package com.example.toptoon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_menu_rv_row, parent, false);
        return new MainMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainMenuRvAdapter.MainMenuViewHolder holder, int position) {
        MainMenuItem menu = (MainMenuItem) getItem(position);
        holder.textView.setText(menu.getTitle());
    }

    static class MainMenuViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MainMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewItem);
        }
    }
}
