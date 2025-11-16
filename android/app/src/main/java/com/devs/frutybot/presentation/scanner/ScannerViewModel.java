package com.devs.frutybot.presentation.scanner;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.devs.frutybot.data.repository.FruitRepository;
import com.devs.frutybot.data.util.RepositoryCallback;
import com.devs.frutybot.domain.usecase.UploadFruitUseCase;

import java.io.File;

public class ScannerViewModel extends AndroidViewModel {
    private final MutableLiveData<String> fruitInfo = new MutableLiveData<>();
    private final UploadFruitUseCase uploadFruitUseCase;

    public ScannerViewModel(@NonNull Application app) {
        super(app);
        uploadFruitUseCase = new UploadFruitUseCase(new FruitRepository());
    }

    public LiveData<String> getFruitInfo() {
        return fruitInfo;
    }

    public void uploadFruit(File photo, String text) {
        uploadFruitUseCase.execute(photo, text, new RepositoryCallback<String>() {
            @Override
            public void onSuccess(String data) {
                fruitInfo.postValue(data);
            }

            @Override
            public void onError(Throwable throwable) {
                fruitInfo.postValue("Error: " + throwable.getMessage());
            }
        });
    }
}