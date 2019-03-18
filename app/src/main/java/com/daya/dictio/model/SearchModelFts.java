package com.daya.dictio.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

@Fts4(contentEntity = DictIndonesia.class)
@Entity
public class SearchModelFts {
    public String word;
    public String meaning;
    @PrimaryKey
    @ColumnInfo(name = "rowid")
    private int rowId;


    public SearchModelFts(int rowId, String word, String meaning) {
        this.rowId = rowId;
        this.word = word;
        this.meaning = meaning;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
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
}
