package com.example.toptoon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SlideImageAdapter extends RecyclerView.Adapter<SlideImageAdapter.SlideImageViewHolder> {
    private Context context;
    private int[] images = new int[]{R.drawable.slide_add_1, R.drawable.slide_add_2, R.drawable.slide_add_3, R.drawable.slide_add_4, R.drawable.slide_add_5,
            R.drawable.slide_add_6, R.drawable.slide_add_7};

    SlideImageAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public SlideImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.slide_image_row, parent, false);
        return new SlideImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlideImageViewHolder holder, int position) {
        holder.imageView.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    static class SlideImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        SlideImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.slideImageRow);
        }
    }
}

