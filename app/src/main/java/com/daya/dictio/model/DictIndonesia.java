package com.daya.dictio.model;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class DictIndonesia {

    @PrimaryKey(autoGenerate = true)
    private
    int idIndo;
    private String word;
    private String meaning;

    @Ignore
    public DictIndonesia(int idIndo) {
        this.idIndo = idIndo;
    }

    @Ignore
    public DictIndonesia(int idIndo, String word, String penjelasn) {
        this.idIndo = idIndo;
        this.word = word;
        this.meaning = penjelasn;
    }

    public DictIndonesia(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }

    public int getIdIndo() {
        return idIndo;
    }

    public void setIdIndo(int idIndo) {
        this.idIndo = idIndo;
    }

    public void setMeaning(int idIndo) {
        this.idIndo = idIndo;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String kata) {
        this.word = kata;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}

