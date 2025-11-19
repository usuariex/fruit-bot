package com.devs.frutybot.presentation.details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devs.frutybot.data.dto.Fruit;
import com.devs.frutybot.data.mapper.FruitMapper;
import com.devs.frutybot.data.remote.ApiService;
import com.devs.frutybot.domain.model.FruitDomain;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class DetailsViewModel extends ViewModel {

    private final MutableLiveData<FruitDomain> fruitLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final ApiService apiService;

    @Inject
    public DetailsViewModel(ApiService apiService) {
        this.apiService = apiService;
    }

    public LiveData<FruitDomain> getFruit() {
        return fruitLiveData;
    }

    public LiveData<String> getError() {
        return errorLiveData;
    }

    public void loadFruitByDepartment(String departamento) {
        apiService.getFruitsByDepartment(departamento).enqueue(new Callback<List<Fruit>>() {
            @Override
            public void onResponse(Call<List<Fruit>> call, Response<List<Fruit>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    // Convertimos el primer Fruit (DTO) a FruitDomain con el mapper
                    FruitDomain fruit = FruitMapper.toDomain(response.body().get(0));
                    fruitLiveData.setValue(fruit);
                } else {
                    errorLiveData.setValue("Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Fruit>> call, Throwable t) {
                errorLiveData.setValue(t.getMessage());
            }
        });
    }
}
