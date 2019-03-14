package com.daya.jojoman.model.db;

import com.daya.jojoman.model.FavoritModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
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

    @Delete
    void deleteAt(FavoritModel favoritModel);

    @Query("SELECT COUNT(idFavorit) FROM favoritmodel")
    LiveData<Integer> getJumlahFavorite();
}
