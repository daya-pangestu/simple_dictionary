package com.daya.dictio.model.db;

import com.daya.dictio.model.HistoryModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface HistoryDao {

    @Query("SELECT * FROM HistoryModel")
    LiveData<List<HistoryModel>> loadhistory();

    @Insert
    void insert(HistoryModel historyModel);

    @Query("DELETE  FROM HistoryModel")
    void delete();


}
