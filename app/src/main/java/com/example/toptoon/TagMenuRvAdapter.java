package com.example.toptoon;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toptoon.databinding.TagMenuRvRowBinding;

public class TagMenuRvAdapter extends ListAdapter<TagMenuItem, TagMenuRvAdapter.TagMenuViewHolder> {
    private int selectedPos = RecyclerView.NO_POSITION;
    private final OnTagSelectedListener listener;
    private final int type; // 0 for default, 1 for custom

    protected TagMenuRvAdapter(int type, OnTagSelectedListener listener) {
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
        this.type = type;
        this.listener = listener;
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

        // 현재 위치가 선택된 위치인지
        if (selectedPos == position) {
            holder.binding.tvTagMenu.setBackgroundResource(R.drawable.tag_menu_selected);
            holder.binding.tvTagMenu.setTextColor(Color.RED);
        } else {
            if (type == 1) { // 1은 custom
                switch (position) {
                    case 0:
                    case 6:
                        holder.binding.tvTagMenu.setBackgroundResource(R.drawable.custom_border_yellow);
                        holder.binding.tvTagMenu.setTextColor(Color.parseColor("#F9A825"));
                        break;
                    case 1:
                    case 4:
                        holder.binding.tvTagMenu.setBackgroundResource(R.drawable.custom_border_green);
                        holder.binding.tvTagMenu.setTextColor(Color.parseColor("#388E3C"));
                        break;
                    case 2:
                    case 5:
                        holder.binding.tvTagMenu.setBackgroundResource(R.drawable.custom_border_purple);
                        holder.binding.tvTagMenu.setTextColor(Color.parseColor("#6A1B9A"));
                        break;
                    case 3:
                    case 7:
                        holder.binding.tvTagMenu.setBackgroundResource(R.drawable.custom_border_red);
                        holder.binding.tvTagMenu.setTextColor(Color.parseColor("#D32F2F"));
                        break;
                }
            } else {
                holder.binding.tvTagMenu.setBackgroundResource(R.drawable.tag_menu_border);
                holder.binding.tvTagMenu.setTextColor(Color.BLACK);
            }
        }

        // 아이템 클릭 이벤트 처리
        holder.itemView.setOnClickListener(v -> {
            notifyItemChanged(selectedPos); // 이전에 선택된 아이템 업데이트
            selectedPos = holder.getLayoutPosition(); // 새로운 위치 저장
            notifyItemChanged(selectedPos); // 새로운 아이템 업데이트

            // Notify the listener
            if (listener != null) {
                listener.onTagSelected(menu.getTitle());
                Log.println(Log.INFO, "TagMenuRvAdapter", "Selected tag: " + menu.getTitle());
            }
        });
    }

    static class TagMenuViewHolder extends RecyclerView.ViewHolder {
        private final TagMenuRvRowBinding binding;

        public TagMenuViewHolder(@NonNull TagMenuRvRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    // 선택된 아이템 위치 설정 메서드
    public void setSelectedItem(int position) {
        notifyItemChanged(selectedPos); // 이전 선택 아이템 업데이트
        selectedPos = position; // 새 위치 설정
        notifyItemChanged(selectedPos); // 새 선택 아이템 업데이트
    }
}
