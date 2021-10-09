package com.example.tvshowv2.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVShowDetails {
    @SerializedName("url")
    private String url;
    @SerializedName("description")
    private String description;
    @SerializedName("runtime")
    private String runTime;
    @SerializedName("image_path")
    private String imagePath;
    @SerializedName("rating")
    private String rating;
    @SerializedName("genres")
    private String[ ] genres;
    @SerializedName("pictures")
    private String [] pictures;

    private List<String> listPicture;
    private List<String> listGenere;

    public TVShowDetails(String url, String description, String runTime, String imagePath, String rating, List<String> listPicture, List<String> listGenere, List<Episode> episodes) {
        this.url = url;
        this.description = description;
        this.runTime = runTime;
        this.imagePath = imagePath;
        this.rating = rating;
        this.listPicture = listPicture;
        this.listGenere = listGenere;
        this.episodes = episodes;
    }

    public List<String> getListPicture() {
        return listPicture;
    }

    public List<String> getListGenere() {
        return listGenere;
    }

    public TVShowDetails() {
    }

    public TVShowDetails(String url, String description, String runTime, String imagePath, String rating, String[] genres, String[] pictures, List<Episode> episodes) {
        this.url = url;
        this.description = description;
        this.runTime = runTime;
        this.imagePath = imagePath;
        this.rating = rating;
        this.genres = genres;
        this.pictures = pictures;
        this.episodes = episodes;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getRunTime() {
        return runTime;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getRating() {
        return rating;
    }

    public String[] getGenres() {
        return genres;
    }

    public String[] getPictures() {
        return pictures;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    @SerializedName("episodes")
    private List<Episode> episodes;
}
