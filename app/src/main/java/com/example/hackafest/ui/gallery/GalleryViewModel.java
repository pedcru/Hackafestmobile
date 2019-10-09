package com.example.hackafest.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> nuevoSolicitante;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Encuesta para ");

        nuevoSolicitante = new MutableLiveData<>();

    }

    public LiveData<String> getTextSolicitante() {
        return nuevoSolicitante;
    }


    public LiveData<String> getText() {
        return mText;
    }
}