package com.daya.dictio.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.daya.dictio.model.DictIndonesia;

public class SharedDataViewModel extends ViewModel {
    //TODO spesific data
    private MutableLiveData<DictIndonesia> dictMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> stringMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<DictIndonesia> getDictIndonesia() {
        return dictMutableLiveData;
    }

    public void setDictIndonesia(DictIndonesia data) {

        dictMutableLiveData.setValue(data);
    }

    public void setStringText(String text) {
        stringMutableLiveData.setValue(text);
    }

    public MutableLiveData<String> getStringText() {
        return stringMutableLiveData;
    }
}
