package com.example.tvshowv2.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshowv2.R;
import com.example.tvshowv2.listeners.TVShowListener;
import com.example.tvshowv2.models.TVShows;
import com.example.tvshowv2.databinding.ItemContinerTvShowBinding;


import java.util.List;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.ViewHolder> {
    private List<TVShows> tvShows;
    private LayoutInflater layoutInflater;
    private TVShowListener listener;

    public TVShowAdapter(List<TVShows> tvShows,TVShowListener listener) {
        this.tvShows = tvShows;
        this.listener = listener;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContinerTvShowBinding itemContinerTvShowBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_continer_tv_show, parent, false
        );
        return new ViewHolder(itemContinerTvShowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindTVShow(tvShows.get(position));

    }

    @Override
    public int getItemCount() {
        return tvShows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemContinerTvShowBinding itemContinerTvShowBinding;

        public ViewHolder(ItemContinerTvShowBinding itemContinerTvShowBinding) {
            super(itemContinerTvShowBinding.getRoot());
            this.itemContinerTvShowBinding = itemContinerTvShowBinding;
        }


        public void bindTVShow(TVShows tvShows) {
            itemContinerTvShowBinding.setTvShows(tvShows);
            itemContinerTvShowBinding.executePendingBindings();
            itemContinerTvShowBinding.getRoot().setOnClickListener(v ->
                    listener.onTVShowCLicked(tvShows)
            );
        }

    }
}
