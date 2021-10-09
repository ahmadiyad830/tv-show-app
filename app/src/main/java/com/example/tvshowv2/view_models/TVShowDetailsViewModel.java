package com.example.tvshowv2.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tvshowv2.models.TVShows;
import com.example.tvshowv2.repositores.TVShowDetailsRepository;
import com.example.tvshowv2.responses.TVShowDetailsResponse;
import com.example.tvshowv2.room_database.TVShowedDatabase;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class TVShowDetailsViewModel extends AndroidViewModel {

    private TVShowDetailsRepository repository;
    private TVShowedDatabase tvShowedDatabase;


    public TVShowDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = new TVShowDetailsRepository();
        tvShowedDatabase = TVShowedDatabase.getInstance(application);

    }

    public LiveData<TVShowDetailsResponse> getTVShowDetails(String tvShowId) {
        return repository.getTVShowDetails(tvShowId);
    }

    public Completable addToWatchList(TVShows tvShows) {
        return tvShowedDatabase.tvShoweDao().addToWatchList(tvShows);
    }

    public Flowable<TVShows> getTVShoweFromListShowedList(String tvShowedId) {
        return tvShowedDatabase.tvShoweDao().getTVShowFromShowedList(tvShowedId);
    }

    public Completable removeTVShowFromShowedList(TVShows tvShows) {
        return tvShowedDatabase.tvShoweDao().removeFromWatchList(tvShows);
    }

}
