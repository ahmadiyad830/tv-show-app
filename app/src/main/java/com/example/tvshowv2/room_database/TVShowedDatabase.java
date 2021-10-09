package com.example.tvshowv2.room_database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tvshowv2.dao.TVShowedDao;
import com.example.tvshowv2.models.TVShows;

@Database(entities = TVShows.class,version = 2,exportSchema = false)
public abstract class TVShowedDatabase extends RoomDatabase {
   private static TVShowedDatabase instance;

   public static synchronized TVShowedDatabase getInstance(Context context){
       if (instance==null){
           instance = Room.databaseBuilder(context.getApplicationContext(),
                   TVShowedDatabase.class,
                   "tv_show_db")
                   .fallbackToDestructiveMigration()
                   .build();
       }
       return instance;
   }
   public abstract TVShowedDao tvShoweDao ();
}
