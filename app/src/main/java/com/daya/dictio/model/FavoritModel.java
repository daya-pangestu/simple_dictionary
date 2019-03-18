package com.daya.dictio.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavoritModel {
    @PrimaryKey(autoGenerate = true)
    int idFavorit;
    private String wordFavorit;
    private String meaning;

    public FavoritModel(int idFavorit, String wordFavorit, String meaning) {
        this.idFavorit = idFavorit;
        this.wordFavorit = wordFavorit;
        this.meaning = meaning;
    }

    public int getIdFavorit() {
        return idFavorit;
    }

    public void setIdFavorit(int idFavorit) {
        this.idFavorit = idFavorit;
    }

    public String getWordFavorit() {
        return wordFavorit;
    }

    public void setWordFavorit(String wordFavorit) {
        this.wordFavorit = wordFavorit;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
