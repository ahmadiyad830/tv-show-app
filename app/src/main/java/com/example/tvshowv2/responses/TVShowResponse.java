package com.example.tvshowv2.responses;

import com.example.tvshowv2.models.TVShows;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TVShowResponse implements Serializable {
    @SerializedName("page")
    private int page;
    @SerializedName("pages")
    private int totalPages;
    @SerializedName("tv_shows")
    private List<TVShows> tvShows;

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<TVShows> getTvShows() {
        return tvShows;
    }
}
