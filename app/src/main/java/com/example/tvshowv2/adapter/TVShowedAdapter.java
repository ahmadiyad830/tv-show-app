package com.example.tvshowv2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshowv2.R;
import com.example.tvshowv2.databinding.ItemContinerTvShowBinding;
import com.example.tvshowv2.listeners.ShowedListListener;
import com.example.tvshowv2.listeners.TVShowListener;
import com.example.tvshowv2.models.TVShows;

import java.util.List;

public class TVShowedAdapter extends RecyclerView.Adapter<TVShowedAdapter.ViewHolder> {
    private List<TVShows> tvShows;
    private LayoutInflater layoutInflater;

    public TVShowedAdapter(List<TVShows> tvShows, ShowedListListener showedListListener) {
        this.tvShows = tvShows;
        this.showedListListener = showedListListener;
    }

    private ShowedListListener showedListListener;


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
            itemContinerTvShowBinding.getRoot().setOnClickListener(v -> showedListListener.onTVShowedClicked(tvShows));
            itemContinerTvShowBinding.imageDeleteFromDb.setVisibility(View.VISIBLE);
            itemContinerTvShowBinding.imageDeleteFromDb.setOnClickListener(v1 ->
            {
                try {
                    showedListListener.removeTVShowedFromShowedList(tvShows, getAdapterPosition());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        }

    }
}
