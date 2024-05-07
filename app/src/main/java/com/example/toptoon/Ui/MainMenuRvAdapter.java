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
        MainMenuItem menu = getItem(position);
        holder.binding.tvItem.setText(menu.getTitle());

        Log.d("MainMenuAdapter", "Binding position: " + position + ", selectedItemPosition: " + selectedItemPosition);

        if ( position  == selectedItemPosition) {
            holder.binding.tvItem.setBackgroundResource(R.drawable.main_menu_border_true);
            Log.d("MainMenuAdapter", "Border true applied to position: " + position);
        } else {
            holder.binding.tvItem.setBackgroundResource(R.drawable.main_menu_border_false);
            Log.d("MainMenuAdapter", "Border false applied to position: " + position);
        }

        holder.itemView.setOnClickListener(v -> {
            int previousSelectedPosition = selectedItemPosition; // 이전 선택된 위치 저장
            selectedItemPosition = position; // 새로운 선택된 위치 설정

            Log.d("MainMenuRvAdapter", "Item clicked at position: " + position + ", previous position: " + previousSelectedPosition + ", new selectedItemPosition: " + selectedItemPosition);

            // 이전과 현재 선택된 위치의 항목만 갱신
            notifyItemChanged(previousSelectedPosition);
            notifyItemChanged(selectedItemPosition);

            if (listener != null) {
                listener.onMainMenuSelected(menu.getTitle()); // Pass the clicked menu item to the listener
                Log.i("MainMenuRvAdapter", "onBindViewHolder: " + menu.getTitle() + " clicked");
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
        this.selectedItemPosition = position;
        notifyDataSetChanged();
    }
}
