package com.example.tvshowv2.listeners;

import com.example.tvshowv2.models.TVShows;

public interface ShowedListListener {
    void onTVShowedClicked(TVShows tvShows);

    void removeTVShowedFromShowedList(TVShows tvShows,int position) throws Exception;

}
