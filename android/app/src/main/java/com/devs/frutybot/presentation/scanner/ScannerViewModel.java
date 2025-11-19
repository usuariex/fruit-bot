package com.devs.frutybot.presentation.scanner;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devs.frutybot.data.dto.UploadResponseStart;
import com.devs.frutybot.data.util.RepositoryCallback;
import com.devs.frutybot.domain.usecase.UploadFruitUseCase;

import java.io.File;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ScannerViewModel extends ViewModel {
    // LiveData que expone la respuesta inmediata del backend
    private final MutableLiveData<UploadResponseStart> fruitInfo = new MutableLiveData<>();
    public LiveData<UploadResponseStart> getFruitInfo() { return fruitInfo; }

    private final UploadFruitUseCase uploadFruitUseCase;

    @Inject
    public ScannerViewModel(UploadFruitUseCase uploadFruitUseCase) {
        this.uploadFruitUseCase = uploadFruitUseCase;
    }

    // Método para subir la foto y texto al backend
    public void uploadFruit(File photo, String text) {
        uploadFruitUseCase.execute(photo, text, new RepositoryCallback<UploadResponseStart>() {
            @Override
            public void onSuccess(UploadResponseStart data) {
                fruitInfo.postValue(data);
            }

            @Override
            public void onError(Throwable throwable) {
                UploadResponseStart errorResponse = new UploadResponseStart();
                errorResponse.setStatus("Error");
                errorResponse.setMessage(throwable.getMessage());
                fruitInfo.postValue(errorResponse);
            }
        });
    }
}
