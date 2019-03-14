package com.daya.jojoman.model.db;

import android.content.Context;

import com.daya.jojoman.model.DictIndonesia;
import com.daya.jojoman.model.FavoritModel;
import com.daya.jojoman.model.HistoryModel;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {DictIndonesia.class, HistoryModel.class, FavoritModel.class}, version = 21, exportSchema = false)
public abstract class DictIndoDatabase extends RoomDatabase {
    public abstract DictIdDao dictIdDao();

    public abstract FavoritDao favoritDao();

    public abstract HistoryDao hDao();

    private static DictIndoDatabase INSTANCE;
    public static DictIndoDatabase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (DictIndoDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            DictIndoDatabase.class,
                            "DictindonesiaDatabase")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    //populate database

}
