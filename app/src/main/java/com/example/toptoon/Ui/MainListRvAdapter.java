package com.example.toptoon.Ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toptoon.DataModel.BaseContentItem;
import com.example.toptoon.databinding.MainListRvRowBinding;

public class MainListRvAdapter extends ListAdapter<BaseContentItem, MainListRvAdapter.MainListRvViewHolder> {
    public MainListRvAdapter() {
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
                    .into(binding.ivImageMain);

            binding.tvTitleMain.setText(baseContentItem.getTitle());
            binding.tvLatestEpisodeMain.setText(baseContentItem.getAuthor());
            binding.tvListViewsMain.setText(baseContentItem.getViews());

//            // 가시성 설정
//            binding.ivHits.setVisibility(tabContentItem.isWaitFree() ? View.VISIBLE : View.GONE );
//            binding.ivNew.setVisibility(tabContentItem.isNew() ? View.VISIBLE : View.GONE );
//            binding.ivDiscounted.setVisibility(tabContentItem.isDiscounted() ? View.VISIBLE : View.GONE );
//            binding.ivUpdate.setVisibility(tabContentItem.isRecentlyUpdated() ? View.VISIBLE : View.GONE );
//            binding.ivExclusive.setVisibility(tabContentItem.isExclusive() ? View.VISIBLE : View.GONE );

        }

    }
}
