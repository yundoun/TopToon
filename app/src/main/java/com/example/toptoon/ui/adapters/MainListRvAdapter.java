package com.example.toptoon.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.toptoon.dto.BaseContentItem;
import com.example.toptoon.databinding.MainListRvRowBinding;

public class MainListRvAdapter extends ListAdapter<BaseContentItem, MainListRvAdapter.MainListRvViewHolder> {

    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(BaseContentItem item);
    }

    public MainListRvAdapter(OnItemClickListener listener) {
        super(new DiffUtil.ItemCallback<BaseContentItem>() {
            @Override
            public boolean areItemsTheSame(@NonNull BaseContentItem oldItem, @NonNull BaseContentItem newItem) {
                return oldItem.equals(newItem);
            }

            @SuppressLint("DiffUtilEquals")
            @Override
            public boolean areContentsTheSame(@NonNull BaseContentItem oldItem, @NonNull BaseContentItem newItem) {
                return oldItem.equals(newItem);
            }
        });
        this.listener = listener;
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
        BaseContentItem baseContentItem = getItem(position);
        holder.bind(baseContentItem);

        // 클릭 리스너 설정
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(baseContentItem);
            }
        });
    }

    public static class MainListRvViewHolder extends RecyclerView.ViewHolder {

    private final MainListRvRowBinding binding;
        public MainListRvViewHolder(MainListRvRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(BaseContentItem baseContentItem) {
            Glide.with(itemView.getContext())
                    .load(baseContentItem.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL) // 모든 버전을 캐시합니다.
                    .into(binding.ivImageMain);

            binding.tvTitleMain.setText(baseContentItem.getTitle());
            binding.tvLatestEpisodeMain.setText(baseContentItem.getAuthor());
            binding.tvListViewsMain.setText(baseContentItem.getViews());

            // 가시성 설정
            binding.ivExclusiveMain.setVisibility(baseContentItem.isExclusive() ? View.VISIBLE : View.GONE );
            binding.ivNewMain.setVisibility(baseContentItem.isNew() ? View.VISIBLE : View.GONE );
            binding.ivSaleMain.setVisibility(baseContentItem.isDiscounted() ? View.VISIBLE : View.GONE );
            binding.ivFreeWaitMain.setVisibility(baseContentItem.isWaitFree() ? View.VISIBLE : View.GONE );
        }

    }
}
