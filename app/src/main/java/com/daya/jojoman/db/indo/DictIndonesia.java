package com.daya.jojoman.db.indo;


import androidx.lifecycle.LiveData;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.annotation.NonNull;

import java.util.List;

@Entity
public class DictIndonesia {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idIndo")
    int idIndo;
    @ColumnInfo(name = "kata")
    String kata;

    @ColumnInfo(name = "penjelasan")
    private String penjelasn;


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
