package com.daya.jojoman.db.indo;


import android.util.Log;

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

import static android.content.ContentValues.TAG;

@Entity
public class DictIndonesia {

    @PrimaryKey(autoGenerate = true)
    int idIndo;

    public String kata;
    public String penjelasn;

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

