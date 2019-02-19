package com.daya.jojoman.db.indo;

import android.util.Log;

import java.util.List;

import androidx.lifecycle.LiveData;
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

    @Transaction
    @Query("SELECT idIndo,kata FROM DictIndonesia")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    LiveData<List<DictIndonesia>> getAllKataOnly();

    @Transaction
    @Query("SELECT  idIndo,kata FROM  DictIndonesia ORDER BY RANDOM() LIMIT 15")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    LiveData<List<DictIndonesia>> get15kataRandom();

    @Insert
    void insert(DictIndonesia dictIndonesia);


    @Update
    abstract void update(DictIndonesia dictIndonesia);

    @Delete
    abstract void delete(DictIndonesia dictIndonesia);




}