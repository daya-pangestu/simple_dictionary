package com.daya.dictio.viewmodel;

import android.app.Application;

import com.daya.dictio.model.HistoryModel;
import com.daya.dictio.model.join.HistoryJoinDict;
import com.daya.dictio.repo.DictRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class HistoryViewModel extends AndroidViewModel {
    private final DictRepository dictRepository;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        dictRepository = new DictRepository(application);
    }

    public void addHistory(HistoryModel hm) {
        dictRepository.addHistory(hm);

    }

    public LiveData<List<HistoryJoinDict>> getList() {
        return dictRepository.gethistory();
    }

    public void deleteHistory() {
        dictRepository.deleteHistory();
    }

    public void deleteHistoryAt(HistoryModel historyModel) {
        dictRepository.deleteHistoryAt(historyModel);
    }


}
