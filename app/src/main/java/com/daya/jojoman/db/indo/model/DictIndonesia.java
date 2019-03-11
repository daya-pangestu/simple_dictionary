package com.daya.jojoman.db.indo.model;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class DictIndonesia {

    @PrimaryKey(autoGenerate = true)
    private
    int idIndo;
    private String kata;
    private String penjelasn;

    @Ignore
    public DictIndonesia(int idIndo) {
        this.idIndo = idIndo;
    }


    public DictIndonesia(String kata, String penjelasn) {
        this.kata = kata;
        this.penjelasn = penjelasn;
    }

    public int getIdIndo() {
        return idIndo;
    }

    public void setIdIndo(int idIndo) {
        this.idIndo = idIndo;
    }

    public String getKata() {
        return kata;
    }

    public void setKata(String kata) {
        this.kata = kata;
    }

    public String getPenjelasn() {
        return penjelasn;
    }

    public void setPenjelasn(String penjelasn) {
        this.penjelasn = penjelasn;
    }
}

