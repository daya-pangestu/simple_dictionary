package com.daya.jojoman;

import android.app.Application;

import com.daya.jojoman.db.indo.DictIdDao;
import com.daya.jojoman.db.indo.DictIndoDatabase;
import com.daya.jojoman.db.indo.DictIndonesia;
import com.daya.jojoman.repo.DictRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class KataViewModel extends AndroidViewModel {

    private DictRepository dictRepository;
    private LiveData<List<DictIndonesia>> limitRandomKata;
    private LiveData<List<DictIndonesia>> allKata;
    private LiveData<List<DictIndonesia>> allKataOnly;
    private DictIndonesia sendToDetail;




    public KataViewModel(@NonNull Application application) {
        super(application);
        dictRepository = new DictRepository(application);
        limitRandomKata = dictRepository.getLimitRandomKata();
        allKata = dictRepository.getAllKata();
        allKataOnly = dictRepository.getAllKataOnly();

    }

    public LiveData<List<DictIndonesia>> getLimitRandomKata() {
        return limitRandomKata;
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

    public void insert(DictIndonesia dictIndonesia) {
        dictRepository.insert(dictIndonesia);
    }


    public DictIndonesia getSendToDetail() {
        return sendToDetail;
    }

    public void setSendToDetail(DictIndonesia sendToDetail) {
        this.sendToDetail = sendToDetail;
    }
}
