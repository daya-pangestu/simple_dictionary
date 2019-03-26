package com.daya.dictio.model.db;

import com.daya.dictio.model.HistoryModel;
import com.daya.dictio.model.join.HistoryJoinDict;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HistoryModel historyModel);

    @Query("DELETE  FROM HistoryModel")
    void delete();

    @Query("DELETE FROM HistoryModel WHERE id = :index")
    void deleteAt(int index);

    @Delete
    void deleteAt(HistoryModel historyModel);


    @Query("SELECT HistoryModel.id,HistoryModel.idOwner,word,meaning FROM HistoryModel JOIN DictIndonesia dict ON HistoryModel.idOwner = dict.idIndo")
    LiveData<List<HistoryJoinDict>> loadhistory();


}
