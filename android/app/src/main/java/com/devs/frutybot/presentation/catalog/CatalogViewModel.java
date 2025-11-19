package com.devs.frutybot.presentation.catalog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devs.frutybot.data.repository.FruitRepository;
import com.devs.frutybot.data.util.RepositoryCallback;
import com.devs.frutybot.domain.model.FruitDomain;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CatalogViewModel extends ViewModel {
    // Ahora usamos FruitDomain en lugar de Fruit
    private final MutableLiveData<List<FruitDomain>> frutas = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final FruitRepository repository;

    @Inject
    public CatalogViewModel(FruitRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<FruitDomain>> getFrutas() {
        return frutas;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void loadFruits(String department) {
        repository.getFruitsByDepartment(department, new RepositoryCallback<List<FruitDomain>>() {
            @Override
            public void onSuccess(List<FruitDomain> data) {
                frutas.postValue(data);
            }

            @Override
            public void onError(Throwable throwable) {
                error.postValue(throwable.getMessage());
            }
        });
    }
}
