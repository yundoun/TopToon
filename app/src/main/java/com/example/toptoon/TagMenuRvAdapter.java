package com.example.toptoon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_menu_rv_row, parent, false);
            return new TagMenuViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TagMenuRvAdapter.TagMenuViewHolder holder, int position) {
            TagMenuItem menu = (TagMenuItem) getItem(position);
            holder.textView.setText(menu.getTitle());
        }

        static class TagMenuViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public TagMenuViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.tvTagMenu);
            }
        }
}
