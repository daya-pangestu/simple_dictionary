package com.daya.dictio.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;


@Entity(
        foreignKeys = @ForeignKey(entity = DictIndonesia.class,

                parentColumns = "idIndo",
                childColumns = "idOwner",
                onDelete = CASCADE),
        indices = {@Index("idOwner")}
)
public class OtherMeaningModel {

    @PrimaryKey(autoGenerate = true)
    public int idOther;
    public String Other;
    private int idOwner;

    @Ignore
    public OtherMeaningModel(String other, int idOwner) {
        Other = other;
        this.idOwner = idOwner;
    }

    public OtherMeaningModel(int idOther, String Other, int idOwner) {
        this.idOther = idOther;
        this.Other = Other;
        this.idOwner = idOwner;
    }

    public int getIdOther() {
        return idOther;
    }

    public void setIdOther(int idOther) {
        this.idOther = idOther;
    }

    public String getOther() {
        return Other;
    }

    public void setOther(String other) {
        Other = other;
    }

    public int getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(int idOwner) {
        this.idOwner = idOwner;
    }
}
