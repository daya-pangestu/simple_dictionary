package com.daya.jojoman.viewmodel;

import android.app.Application;

import com.daya.jojoman.model.DictIndonesia;
import com.daya.jojoman.model.HistoryModel;
import com.daya.jojoman.repo.DictRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class HistoryViewModel extends AndroidViewModel {
    List<DictIndonesia> list;
    private final DictRepository dictRepository;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        dictRepository = new DictRepository(application);
    }

    public void addHistory(HistoryModel hm) {
        //insert transaction to db tabel history

        //list.add(d)

        dictRepository.addHistory(hm);

    }

    public LiveData<List<HistoryModel>> getList() {
        return dictRepository.gethistory();
    }

    public void deleteHistory() {


        dictRepository.deleteHistory();
    }


}
