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
    private LiveData<List<DictIndonesia>> allKata;


    public KataViewModel(@NonNull Application application) {
        super(application);
        dictRepository = new DictRepository(application);
        allKata = dictRepository.getAllKata();
    }

    public LiveData<List<DictIndonesia>> getAllKataKata() {
        return allKata;
    }

    public void inserttransactional(DictIndonesia dictIndonesia) {
        dictRepository.insertTransaction(dictIndonesia);

    }

    public void insert(DictIndonesia dictIndonesia) {
        dictRepository.insert(dictIndonesia);
    }

}
