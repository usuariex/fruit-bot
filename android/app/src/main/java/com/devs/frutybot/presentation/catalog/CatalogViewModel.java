package com.devs.frutybot.presentation.catalog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devs.frutybot.data.repository.FruitRepositoryImpl;
import com.devs.frutybot.presentation.common.Fruit;

import java.util.List;

public class CatalogViewModel extends ViewModel {
    private final FruitRepositoryImpl repository = new FruitRepositoryImpl();
    private final MutableLiveData<List<Fruit>> fruits = new MutableLiveData<>();

    public LiveData<List<Fruit>> getFruits() {
        return fruits;
    }

    public void loadFruits() {
        repository.getFruits().observeForever(fruits::setValue);
    }
}