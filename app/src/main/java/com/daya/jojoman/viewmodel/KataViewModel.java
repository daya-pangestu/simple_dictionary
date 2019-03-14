package com.daya.jojoman.viewmodel;

import android.app.Application;

import com.daya.jojoman.model.DictIndonesia;
import com.daya.jojoman.repo.DictRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class KataViewModel extends AndroidViewModel {

    private final DictRepository dictRepository;
    private final LiveData<List<DictIndonesia>> allKata;
    private final LiveData<List<DictIndonesia>> allKataOnly;
    private DictIndonesia sendToDetail;


    public KataViewModel(@NonNull Application application) {
        super(application);
        dictRepository = new DictRepository(application);
        allKata = dictRepository.getAllKata();
        allKataOnly = dictRepository.getAllKataOnly();


    }
    public LiveData<List<DictIndonesia>> getSearch(String s) {
        LiveData<List<DictIndonesia>> search;
        return search = dictRepository.getSearch(s);
    }


    public LiveData<List<DictIndonesia>> getAllKata() {
        return allKata;
    }

    public LiveData<List<DictIndonesia>> getAllKataOnly() {
        return allKataOnly;
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
