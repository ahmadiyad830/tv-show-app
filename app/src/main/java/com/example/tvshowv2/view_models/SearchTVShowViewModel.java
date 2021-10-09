package com.example.tvshowv2.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tvshowv2.repositores.SearchTVShowRepository;
import com.example.tvshowv2.responses.TVShowResponse;

public class SearchTVShowViewModel extends ViewModel {
    private SearchTVShowRepository searchTVShowRepository ;

    public SearchTVShowViewModel() {
        searchTVShowRepository = new SearchTVShowRepository();
    }
    public LiveData<TVShowResponse> SearchTVSHow(String query,int page){
        return searchTVShowRepository.searchTVShow(query,page);
    }
}
