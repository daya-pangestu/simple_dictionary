package com.daya.dictio.model.db;

import com.daya.dictio.model.DictIndonesia;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public abstract class DictIdDao {

    @Query("SELECT * FROM DictIndonesia")
    public abstract LiveData<List<DictIndonesia>> getAll();

    @Query("SELECT * FROM DictIndonesia")
    public abstract DataSource.Factory<Integer, DictIndonesia> getAllPaged();


    @Query("SELECT DictIndonesia.* FROM DictIndonesia JOIN SearchModelFts ON (DictIndonesia.idIndo = SearchModelFts.`rowid`) WHERE SearchModelFts.word MATCH  :word LIMIT 500 ")
    public abstract LiveData<List<DictIndonesia>> getSearchQuery(String word);


    @Query("SELECT DictIndonesia.* FROM DictIndonesia JOIN SearchModelFts ON (DictIndonesia.idIndo = SearchModelFts.`rowid`) WHERE SearchModelFts.word MATCH  :word LIMIT 500 ")
    public abstract DataSource.Factory<Integer, DictIndonesia> getSearchQueryPaged(String word);


    @Insert
    public abstract void insert(DictIndonesia dictIndonesia);


    @Delete
    public abstract void delete(DictIndonesia dictIndonesia);

    @Transaction
    public void insertTransac(DictIndonesia dictIndonesia) {
        insert(dictIndonesia);
    }

    @Transaction
    public DataSource.Factory<Integer, DictIndonesia> getSearchTransac(String s) {
        return getSearchQueryPaged(s);
    }


}
