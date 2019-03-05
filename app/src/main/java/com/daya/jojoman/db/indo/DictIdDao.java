package com.daya.jojoman.db.indo;

import android.util.Log;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;
import androidx.room.Update;

@Dao
public interface DictIdDao {

    @Query("SELECT * FROM DictIndonesia")
    LiveData<List<DictIndonesia>> getAll();

    @Query("SELECT * FROM DictIndonesia WHERE kata LIKE  '%' || :word  || '%' ")
    LiveData<List<DictIndonesia>> getAllsearch(String word);


    @Query("SELECT * FROM DictIndonesia")
    public abstract DataSource.Factory<Integer, DictIndonesia> getAllPaged();


    @Transaction
    @Query("SELECT idIndo,kata FROM DictIndonesia")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)

    LiveData<List<DictIndonesia>> getAllKataOnly();

    @Transaction

    @Query("SELECT * FROM DictIndonesia WHERE idIndo IN (SELECT idIndo FROM dictindonesia ORDER BY RANDOM() LIMIT 10)")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    LiveData<List<DictIndonesia>> get15kataRandom();

    @Insert
    void insert(DictIndonesia dictIndonesia);


    @Update
    abstract void update(DictIndonesia dictIndonesia);

    @Delete
    abstract void delete(DictIndonesia dictIndonesia);

}
