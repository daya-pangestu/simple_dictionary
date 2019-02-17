package com.daya.jojoman.db.eng;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;
import androidx.annotation.NonNull;

import java.util.List;

@Entity
public class DictEnglish {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "idEng")
    int idEng;

    @ColumnInfo(name = "word")
    String word;
    @ColumnInfo(name = "meaning")
    private String meaning;

    public DictEnglish(int idEng, String word, String meaning) {
        this.idEng = idEng;
        this.word = word;
        this.meaning = meaning;
    }

    public int getIdEng() {
        return idEng;
    }

    public void setIdEng(int idEng) {
        this.idEng = idEng;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Dao
    public interface DictEngDao {
        @Query("SELECT * FROM DictEnglish")
        List<DictEnglish> getAll();


        @Query("SELECT idEng,word FROM  DictEnglish")
        @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
        List<DictEnglish> getListWordOnly();


        @Insert
        void insert(DictEnglish dictEnglish);

        @Update
        void update(DictEnglish dictEnglish);

        @Delete
        void delete(DictEnglish dictEnglish);
    }
}

