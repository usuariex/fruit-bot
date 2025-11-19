package com.devs.frutybot.presentation.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devs.frutybot.data.dto.Fruit;
import com.devs.frutybot.data.mapper.FruitMapper;
import com.devs.frutybot.data.repository.FruitRepository;
import com.devs.frutybot.data.util.RepositoryCallback;
import com.devs.frutybot.domain.model.FruitDomain;

import java.util.List;

public class SearchViewModel extends ViewModel {

    private final MutableLiveData<List<FruitDomain>> fruits = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final FruitRepository repository = new FruitRepository();

    public LiveData<List<FruitDomain>> getFruits() {
        return fruits;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void fetchFruits(String query) {
        repository.searchFruits(query, new RepositoryCallback<List<FruitDomain>>() {
            @Override
            public void onSuccess(List<FruitDomain> data) {
                fruits.postValue(data);
            }

            @Override
            public void onError(Throwable throwable) {
                error.postValue(throwable.getMessage());
            }
        });
    }

}
