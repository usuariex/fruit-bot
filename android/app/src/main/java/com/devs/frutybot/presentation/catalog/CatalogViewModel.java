package com.devs.frutybot.presentation.catalog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devs.frutybot.data.repository.FruitRepository;
import com.devs.frutybot.data.util.RepositoryCallback;
import com.devs.frutybot.domain.model.Fruit;

import java.util.List;

public class CatalogViewModel extends ViewModel {
    private final MutableLiveData<List<Fruit>> frutas = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final FruitRepository repository = new FruitRepository();

    public LiveData<List<Fruit>> getFrutas() {
        return frutas;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void loadFruits(String department) {
        repository.getFruitsByDepartment(department, new RepositoryCallback<List<Fruit>>() {
            @Override
            public void onSuccess(List<Fruit> data) {
                frutas.postValue(data);
            }

            @Override
            public void onError(Throwable throwable) {
                error.postValue(throwable.getMessage());
            }
        });
    }

}
