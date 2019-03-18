package com.daya.dictio.model.db;

import com.daya.dictio.model.DictIndonesia;

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

    @Query("SELECT DictIndonesia.* FROM DictIndonesia JOIN SearchModelFts ON (DictIndonesia.idIndo = SearchModelFts.`rowid`) WHERE SearchModelFts.word MATCH  :word ")
    LiveData<List<DictIndonesia>> getSearchQuery(String word);


    @Transaction
    @Query("SELECT * FROM DictIndonesia WHERE idIndo IN (SELECT idIndo FROM dictindonesia ORDER BY RANDOM() LIMIT 10)")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    LiveData<List<DictIndonesia>> get15kataRandom();
    @Insert
    void insert(DictIndonesia dictIndonesia);


    @Update
    void update(DictIndonesia dictIndonesia);

    @Delete
    void delete(DictIndonesia dictIndonesia);


}
