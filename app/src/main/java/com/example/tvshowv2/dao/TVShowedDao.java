package com.example.tvshowv2.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tvshowv2.models.TVShows;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface TVShowedDao {
    @Query("SELECT * FROM tvShowed")
    Single<List<TVShows>> getWatshList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addToWatchList(TVShows tvShows);

    @Delete
    Completable removeFromWatchList(TVShows tvShows);

    @Query("SELECT * FROM tvShowed WHERE id = :tvShowId")
    Flowable<TVShows> getTVShowFromShowedList(String tvShowId);
}
