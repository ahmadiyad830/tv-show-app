package com.example.tvshowv2.responses;

import com.example.tvshowv2.models.TVShowDetails;
import com.google.gson.annotations.SerializedName;

public class TVShowDetailsResponse {

    @SerializedName("tvShow")
    private TVShowDetails tvShowDetails;
    public TVShowDetails getTvShowDetails() {
        return tvShowDetails;
    }
}
