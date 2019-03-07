package com.daya.jojoman.db.indo;

import com.daya.jojoman.db.indo.model.HistoryModel;
import com.daya.jojoman.db.indo.model.relation.History;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface HistoryDao {

    @Query("SELECT * FROM HistoryModel")
    LiveData<List<HistoryModel>> loadhistory();

    @Insert
    void insert(HistoryModel historyModel);

    @Query("DELETE FROM HistoryModel")
    void delete();
}
