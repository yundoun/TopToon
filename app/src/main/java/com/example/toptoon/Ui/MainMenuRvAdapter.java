package com.example.toptoon.Ui;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toptoon.DataModel.MainMenuItem;
import com.example.toptoon.R;
import com.example.toptoon.databinding.MainMenuRvRowBinding;

public class MainMenuRvAdapter extends ListAdapter<MainMenuItem, MainMenuRvAdapter.MainMenuViewHolder> {

    private OnMainMenuSelectedListener listener;
    private int selectedItemPosition = -1; // 선택된 아이템이 없는 상태를 -1로 초기화

    public void setListener(OnMainMenuSelectedListener listener) {
        this.listener = listener;
    }

    public MainMenuRvAdapter() {
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

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull MainMenuRvAdapter.MainMenuViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MainMenuItem item = getItem(position);
        holder.binding.tvItem.setText(item.getTitle());

        Log.d("MainMenuAdapter", "Binding position: " + position + ", selectedItemPosition: " + selectedItemPosition);


        // 선택된 항목의 테두리를 빨간색으로 변경
        if (position == selectedItemPosition && position >= 0 && position <= 3) {
            holder.binding.tvItem.setBackgroundResource(R.drawable.main_menu_border_true);
        } else {
            holder.binding.tvItem.setBackgroundResource(R.drawable.main_menu_border_false); // 기본 배경을 설정하거나 초기화
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMainMenuSelected(item.getTitle());
                setSelectedItemPosition(position); // 선택된 항목 업데이트
            }
        });

    }



    static class MainMenuViewHolder extends RecyclerView.ViewHolder {
        private final MainMenuRvRowBinding binding;

        public MainMenuViewHolder(@NonNull MainMenuRvRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setSelectedItemPosition(int position) {
        int previousPosition = selectedItemPosition;
        selectedItemPosition = position;
        if (previousPosition != -1) {
            notifyItemChanged(previousPosition);
        }
        if (position != -1) {
            notifyItemChanged(position);
        }
    }
//    // 상태 저장 메소드
//    public int getSelectedItemPosition() {
//        return selectedItemPosition;
//    }


}
