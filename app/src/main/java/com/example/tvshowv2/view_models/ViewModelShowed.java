package com.example.tvshowv2.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.tvshowv2.models.TVShows;
import com.example.tvshowv2.room_database.TVShowedDatabase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class ViewModelShowed extends AndroidViewModel {
    private  TVShowedDatabase tvShowedDatabase;

    public ViewModelShowed(@NonNull Application application) {
        super(application);
        tvShowedDatabase = TVShowedDatabase.getInstance(application);

    }

    public Completable removeFromShowedList(TVShows tvShows){
        return tvShowedDatabase.tvShoweDao().removeFromWatchList(tvShows);
    }
    public Single<List<TVShows>> loadWatchedList() {
        return tvShowedDatabase.tvShoweDao().getWatshList();
    }
}
