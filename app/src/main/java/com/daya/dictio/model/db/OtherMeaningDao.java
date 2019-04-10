package com.daya.dictio.model.db;

import com.daya.dictio.model.OtherMeaningModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface OtherMeaningDao {

    @Insert
    void insertOther(OtherMeaningModel otherMeaning);

    @Query("SELECT * FROM  othermeaningmodel WHERE idOwner Like :s ")
    LiveData<List<OtherMeaningModel>> getOtherMeaning(int s);

    @Delete
    void deleteOtherMeaning(OtherMeaningModel otherMeaningModel);

    @Update
    void updateOtherMeaning(OtherMeaningModel... otherMeaningModels);
}
