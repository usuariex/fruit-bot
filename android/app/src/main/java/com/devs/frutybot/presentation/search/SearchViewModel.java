package com.devs.frutybot.presentation.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devs.frutybot.data.dto.FruitDto;
import com.devs.frutybot.data.remote.ApiClient;
import com.devs.frutybot.domain.model.Fruit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {

    private final MutableLiveData<List<Fruit>> _fruits = new MutableLiveData<>();
    public LiveData<List<Fruit>> fruits = _fruits;

    public void fetchFruits(String query) {
        if (query.isEmpty()) {
            _fruits.postValue(new ArrayList<>());
            return;
        }

        ApiClient.getApiService().getFruitsByDepartment(query)
                .enqueue(new Callback<List<FruitDto>>() {
                    @Override
                    public void onResponse(Call<List<FruitDto>> call, Response<List<FruitDto>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Fruit> list = new ArrayList<>();
                            for (FruitDto dto : response.body()) {
                                // Usamos id ficticio 0 para cumplir el constructor de Fruit

                            }
                            _fruits.postValue(list);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<FruitDto>> call, Throwable t) {
                        // Puedes agregar un LiveData de error si quieres
                        _fruits.postValue(new ArrayList<>());
                    }
                });
    }
}
