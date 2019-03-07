package com.daya.jojoman.repo;

import android.app.Application;

import com.daya.jojoman.db.indo.model.DictIndonesia;
import com.daya.jojoman.db.indo.model.HistoryModel;
import com.daya.jojoman.db.indo.model.relation.History;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class HistoryViewModel extends AndroidViewModel {
    List<DictIndonesia> list;
    DictRepository dictRepository;

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

   /* public void deleteHistory() {

        //remove transaction to db tabel history
       // list.clear();
        dictRepository.deleteAllHistory();
    }*/


}
