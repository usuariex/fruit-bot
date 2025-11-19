package com.devs.frutybot.domain.usecase;

import com.devs.frutybot.data.dto.UploadResponseStart;
import com.devs.frutybot.data.repository.FruitRepository;
import com.devs.frutybot.data.util.RepositoryCallback;

import java.io.File;

import javax.inject.Inject;

public class UploadFruitUseCase {
    private final FruitRepository repository;

    @Inject
    public UploadFruitUseCase(FruitRepository repository) {
        this.repository = repository;
    }

    public void execute(File photo, String text, RepositoryCallback<UploadResponseStart> callback) {
        repository.uploadPhoto(photo, text, callback);
    }
}
