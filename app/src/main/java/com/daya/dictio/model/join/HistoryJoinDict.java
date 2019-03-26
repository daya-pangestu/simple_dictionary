package com.daya.dictio.model.join;

public class HistoryJoinDict {
    private int id;
    private int idOwner;
    private String word;
    private String meaning;


    public HistoryJoinDict(int id, int idOwner, String word, String meaning) {
        this.id = id;
        this.idOwner = idOwner;
        this.word = word;
        this.meaning = meaning;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(int idOwner) {
        this.idOwner = idOwner;
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
