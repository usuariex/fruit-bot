package com.devs.frutybot.presentation.scanner;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devs.frutybot.data.dto.UploadResponse;
import com.devs.frutybot.data.repository.FruitRepository;
import com.devs.frutybot.data.util.RepositoryCallback;
import com.devs.frutybot.domain.usecase.UploadFruitUseCase;

import java.io.File;

public class ScannerViewModel extends AndroidViewModel {
    // LiveData que expone la respuesta completa del backend
    private final MutableLiveData<UploadResponse> fruitInfo = new MutableLiveData<>();
    public LiveData<UploadResponse> getFruitInfo() { return fruitInfo; }

    private final UploadFruitUseCase uploadFruitUseCase;

    public ScannerViewModel(@NonNull Application app) {
        super(app);
        uploadFruitUseCase = new UploadFruitUseCase(new FruitRepository());
    }

    // Método para subir la foto y texto al backend
    public void uploadFruit(File photo, String text) {
        uploadFruitUseCase.execute(photo, text, new RepositoryCallback<UploadResponse>() {
            @Override
            public void onSuccess(UploadResponse data) {
                // Posteamos el objeto completo con requestId, status, imageUrl, message
                fruitInfo.postValue(data);
            }

            @Override
            public void onError(Throwable throwable) {
                UploadResponse errorResponse = new UploadResponse();
                errorResponse.setStatus("Error");
                errorResponse.setMessage(throwable.getMessage());
                fruitInfo.postValue(errorResponse);
            }

        });
    }
}
