package com.daya.dictio.model.db;

import com.daya.dictio.model.FavoritModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface FavoriteDao {
    @Query("SELECT * FROM FavoritModel")
    LiveData<List<FavoritModel>> loadFavorite();

    @Insert
    void insert(FavoritModel favoritModel);

    @Query("DELETE  FROM FavoritModel")
    void delete();

    @Delete
    void deleteAt(FavoritModel favoritModel);

    @Query("SELECT COUNT(idFavorit) FROM favoritmodel")
    LiveData<Integer> getTotalFavorite();

    @Query("SELECT wordFavorit From favoritmodel WHERE :s")
    String isfavoritExists(String s);
}

