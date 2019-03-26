package com.daya.dictio.model.db;

import com.daya.dictio.model.FavoritModel;
import com.daya.dictio.model.join.FavoriteJoinDict;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoritModel favoritModel);

    @Query("DELETE  FROM FavoritModel")
    void delete();

    @Delete
    void deleteAt(FavoritModel favoritModel);


    @Query("SELECT COUNT(id) FROM favoritmodel")
    LiveData<Integer> getTotalFavorite();


    @Query("SELECT idOwner From favoritmodel WHERE :s")
    Integer isfavoritExists(int s);

    @Query("SELECT FavoritModel.id,FavoritModel.idOwner,word,meaning  FROM FavoritModel JOIN DictIndonesia ON FavoritModel.idOwner = DictIndonesia.idIndo")
    LiveData<List<FavoriteJoinDict>> loadFavorite();
}

