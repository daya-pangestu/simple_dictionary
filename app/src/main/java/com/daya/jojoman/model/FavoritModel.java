package com.daya.jojoman.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavoritModel {
    @PrimaryKey(autoGenerate = true)
    int idFavorit;
    private String kataFavorit;
    private String penjelasan;

    public FavoritModel(int idFavorit, String kataFavorit, String penjelasan) {
        this.idFavorit = idFavorit;
        this.kataFavorit = kataFavorit;
        this.penjelasan = penjelasan;
    }

    public int getIdFavorit() {
        return idFavorit;
    }

    public void setIdFavorit(int idFavorit) {
        this.idFavorit = idFavorit;
    }

    public String getKataFavorit() {
        return kataFavorit;
    }

    public void setKataFavorit(String kataFavorit) {
        this.kataFavorit = kataFavorit;
    }

    public String getPenjelasan() {
        return penjelasan;
    }

    public void setPenjelasan(String penjelasan) {
        this.penjelasan = penjelasan;
    }

}
