package com.daya.dictio.model.db;

import android.content.Context;

import com.daya.dictio.model.DictIndonesia;
import com.daya.dictio.model.FavoritModel;
import com.daya.dictio.model.HistoryModel;
import com.daya.dictio.model.SearchModelFts;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {DictIndonesia.class, FavoritModel.class, HistoryModel.class, SearchModelFts.class}, version = 25, exportSchema = false)
public abstract class DictIndoDatabase extends RoomDatabase {
    public abstract DictIdDao dictIdDao();

    private static Migration migrationFTS = new Migration(25, 26) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS `DictindonesiaFts` USING FTS4("
                    + "`word`,`meaning` , content=`DictIndoDatabase` )");
            database.execSQL("INSERT INTO DictindonesiaFts(`rowid`,`word`,`meaning`)"
                    + " SELECT `idindo`,`word`,`meaning` FROM DictIndoDatabase ");
        }
    };
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
                            .addMigrations(migrationFTS)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract FavoriteDao favoriteDao();

    public abstract HistoryDao historyDao();

    //populate database

}
