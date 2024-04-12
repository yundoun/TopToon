package com.example.toptoon;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toptoon.databinding.TagMenuRvRowBinding;

public class TagMenuRvAdapter extends ListAdapter<TagMenuItem, TagMenuRvAdapter.TagMenuViewHolder> {
    private int selectedPos = RecyclerView.NO_POSITION;

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
        // 기본 배경색 변수
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

        // 현재 위치가 선택된 위치인지 확인
        if (selectedPos == position) {
            // 선택된 배경 적용
            holder.binding.tvTagMenu.setBackgroundResource(R.drawable.tag_menu_selected);
            // 글자색 빨간색으로 변경
            holder.binding.tvTagMenu.setTextColor(Color.RED);
        } else {
            // 기본 배경 적용
            holder.binding.tvTagMenu.setBackgroundResource(R.drawable.tag_menu_border);
            // 글자색 검정색으로 변경
            holder.binding.tvTagMenu.setTextColor(Color.BLACK);
        }

        // 아이템 클릭 이벤트 처리
        holder.itemView.setOnClickListener(v -> {
            notifyItemChanged(selectedPos); // 이전에 선택된 아이템 업데이트
            selectedPos = holder.getLayoutPosition(); // 새로운 위치 저장
            notifyItemChanged(selectedPos); // 새로운 아이템 업데이트
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
