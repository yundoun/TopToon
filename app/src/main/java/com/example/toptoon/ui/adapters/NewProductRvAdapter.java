package com.example.toptoon.ui.adapters;

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

public class NewProductRvAdapter extends ListAdapter<BaseContentItem, NewProductRvAdapter.NewProductRvViewHolder>{

    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(BaseContentItem item);
    }

    public NewProductRvAdapter(OnItemClickListener listener) {
        super(new DiffUtil.ItemCallback<BaseContentItem>() {
            @Override
            public boolean areItemsTheSame(@NonNull BaseContentItem oldItem, @NonNull BaseContentItem newItem) {
                // 각 항목을 고유하게 식별할 수 있는 속성을 비교 (예: imageUrl)
                return oldItem.getImageUrl().equals(newItem.getImageUrl());
            }

            @Override
            public boolean areContentsTheSame(@NonNull BaseContentItem oldItem, @NonNull BaseContentItem newItem) {
                // 항목의 상세 내용이 같은지 비교 (equals 메소드 사용 권장)
                return oldItem.equals(newItem);
            }
        });
        this.listener = listener;
    }


    @NonNull
    @Override
    public NewProductRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MainListRvRowBinding binding = MainListRvRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NewProductRvViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewProductRvAdapter.NewProductRvViewHolder holder, int position) {
        BaseContentItem baseContentItem = getItem(position);
        holder.bind(baseContentItem);

        // 클릭 리스너 설정
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(baseContentItem);
            }
        });
    }

    public static class NewProductRvViewHolder extends RecyclerView.ViewHolder {

        private final MainListRvRowBinding binding;
        public NewProductRvViewHolder(MainListRvRowBinding binding) {
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
