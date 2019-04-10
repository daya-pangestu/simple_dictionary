package com.daya.dictio.viewmodel;

import android.app.Application;

import com.daya.dictio.model.OtherMeaningModel;
import com.daya.dictio.repo.DictRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class OtherViewModel extends AndroidViewModel {
    private final DictRepository dictRepository;

    public OtherViewModel(@NonNull Application application) {
        super(application);
        dictRepository = new DictRepository(application);
    }

    public void addMeaning(OtherMeaningModel otherMeaning) {
        dictRepository.addMeaning(otherMeaning);

    }

    public LiveData<List<OtherMeaningModel>> getOtherMeaning(int s) {
        return dictRepository.getOtherMeaning(s);
    }

    public void deleteOtherMeaning(OtherMeaningModel otherMeaningModel) {
        dictRepository.deleteOtherMeaning(otherMeaningModel);
    }

    public void updateOtherMeaning(OtherMeaningModel... otherMeaningModels) {
        dictRepository.updateOtherMeaning(otherMeaningModels);
    }

}
