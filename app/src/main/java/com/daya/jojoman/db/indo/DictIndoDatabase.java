package com.daya.jojoman.db.indo;

import android.content.Context;

import com.daya.jojoman.repo.DictRepository;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {DictIndonesia.class}, version = 7, exportSchema = false)
public abstract class DictIndoDatabase extends RoomDatabase {
    public abstract DictIdDao dictIdDao();

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

}
