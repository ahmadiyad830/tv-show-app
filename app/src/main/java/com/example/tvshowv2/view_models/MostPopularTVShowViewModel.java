package com.example.tvshowv2.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tvshowv2.repositores.MostPopularTVShowsRepository;
import com.example.tvshowv2.responses.TVShowResponse;


public class MostPopularTVShowViewModel extends ViewModel {
    private final MostPopularTVShowsRepository mostPopularTVShowsRepository;

    public MostPopularTVShowViewModel() {
        mostPopularTVShowsRepository = new MostPopularTVShowsRepository();
    }
    public LiveData<TVShowResponse> getMostPopularTVShow(int page){
        return mostPopularTVShowsRepository.getMostPopularTVShows(page);
    }
}
