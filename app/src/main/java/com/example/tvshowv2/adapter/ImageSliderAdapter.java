package com.example.tvshowv2.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshowv2.R;
import com.example.tvshowv2.databinding.ItemCountinerSliderImageBinding;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>{
    private String [] sliderImage;
    private LayoutInflater layoutInflater;

    public ImageSliderAdapter(String[] sliderImage) {
        this.sliderImage = sliderImage;
    }

    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater==null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemCountinerSliderImageBinding itemCountinerSliderImageBinding = DataBindingUtil.inflate(layoutInflater,R.layout.item_countiner_slider_image,parent,false);

        return new ImageSliderViewHolder(itemCountinerSliderImageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {
        holder.bindSliderImage(sliderImage[position]);
    }

    @Override
    public int getItemCount() {
        return sliderImage.length;
    }

    static class ImageSliderViewHolder extends RecyclerView.ViewHolder {
     private ItemCountinerSliderImageBinding itemCountinerSliderImageBinding;

        public ImageSliderViewHolder(ItemCountinerSliderImageBinding itemCountinerSliderImageBinding) {
            super(itemCountinerSliderImageBinding.getRoot());
            this.itemCountinerSliderImageBinding = itemCountinerSliderImageBinding;
        }
        public void bindSliderImage(String imageURL){
            itemCountinerSliderImageBinding.setImageURL(imageURL);
        }

    }
}
