package com.devs.frutybot.presentation.details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devs.frutybot.data.dto.FruitDto;
import com.devs.frutybot.data.remote.ApiClient;
import com.devs.frutybot.data.remote.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsViewModel extends ViewModel {

    private MutableLiveData<FruitDto> fruitLiveData = new MutableLiveData<>();

    public LiveData<FruitDto> getFruit() {
        return fruitLiveData;
    }

    public void loadFruitByDepartment(String departamento) {
        ApiService apiService = ApiClient.getApiService();
        apiService.getFruitsByDepartment(departamento).enqueue(new Callback<List<FruitDto>>() {
            @Override
            public void onResponse(Call<List<FruitDto>> call, Response<List<FruitDto>> response) {
                if(response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    // Tomamos la primera fruta de la lista como ejemplo
                    fruitLiveData.setValue(response.body().get(0));
                }
            }

            @Override
            public void onFailure(Call<List<FruitDto>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
