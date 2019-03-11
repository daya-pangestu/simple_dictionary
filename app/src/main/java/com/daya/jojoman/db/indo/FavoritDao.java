package com.daya.jojoman.db.indo;

import com.daya.jojoman.db.indo.model.FavoritModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface FavoritDao {
    @Query("SELECT * FROM FavoritModel")
    LiveData<List<FavoritModel>> loadFavorite();

    @Insert
    void insert(FavoritModel favoritModel);

    @Query("DELETE  FROM FavoritModel")
    void delete();

}
