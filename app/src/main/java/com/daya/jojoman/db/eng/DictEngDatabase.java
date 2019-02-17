package com.daya.jojoman.db.eng;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DictEnglish.class}, version = 1, exportSchema = false)
public abstract class DictEngDatabase extends RoomDatabase{
    public abstract DictEnglish.DictEngDao getEngDao();
}


