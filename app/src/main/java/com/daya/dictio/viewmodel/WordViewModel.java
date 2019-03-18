package com.daya.dictio.viewmodel;

import android.app.Application;

import com.daya.dictio.model.DictIndonesia;
import com.daya.dictio.repo.DictRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class WordViewModel extends AndroidViewModel {

    private final DictRepository dictRepository;
    private final LiveData<List<DictIndonesia>> allWord;
    private DictIndonesia sendToDetail;


    public WordViewModel(@NonNull Application application) {
        super(application);
        dictRepository = new DictRepository(application);
        allWord = dictRepository.getAllWord();


    }
    public LiveData<List<DictIndonesia>> getSearch(String s) {
        return dictRepository.getSearch(s);
    }


    public LiveData<List<DictIndonesia>> getAllWord() {
        return allWord;
    }


    public void inserttransactional(DictIndonesia dictIndonesia) {
        dictRepository.insertTransaction(dictIndonesia);

    }

    public DictIndonesia getSendToDetail() {
        return sendToDetail;
    }

    public void setSendToDetail(DictIndonesia sendToDetail) {
        this.sendToDetail = sendToDetail;
    }

}
