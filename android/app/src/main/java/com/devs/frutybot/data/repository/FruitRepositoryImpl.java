package com.devs.frutybot.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devs.frutybot.data.dto.FruitDto;
import com.devs.frutybot.data.remote.ApiClient;
import com.devs.frutybot.data.remote.ApiService;
import com.devs.frutybot.presentation.common.Fruit;
import com.devs.frutybot.data.mapper.FruitMapper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FruitRepositoryImpl {
    /*private final ApiService api;

    public FruitRepositoryImpl() {
        this.api = ApiClient.getApiService();
    }

    public LiveData<List<Fruit>> getFruits() {
        MutableLiveData<List<Fruit>> data = new MutableLiveData<>();

        api.getFruits().enqueue(new Callback<List<FruitDto>>() {
            @Override
            public void onResponse(Call<List<FruitDto>> call, Response<List<FruitDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Fruit> fruits = new ArrayList<>();
                    for (FruitDto dto : response.body()) {
                        fruits.add(FruitMapper.toUiModel(dto));
                    }
                    data.postValue(fruits);
                }
            }

            @Override
            public void onFailure(Call<List<FruitDto>> call, Throwable t) {
                data.postValue(new ArrayList<>());
            }
        });

        return data;
    }*/


    public List<Fruit> getFruits() {
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit("Manzana", "Rojo", 3));
        fruits.add(new Fruit("Banana", "Amarillo", 2));
        fruits.add(new Fruit("Naranja", "Naranja", 4));
        fruits.add(new Fruit("Uva", "Morado", 5));
        fruits.add(new Fruit("Sandía", "Verde", 7));
        return fruits;
    }
}
