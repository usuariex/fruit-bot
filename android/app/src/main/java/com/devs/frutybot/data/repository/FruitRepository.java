package com.devs.frutybot.data.repository;

import com.devs.frutybot.data.dto.Fruit;
import com.devs.frutybot.data.dto.UploadResponseStart;
import com.devs.frutybot.data.remote.ApiClient;
import com.devs.frutybot.data.remote.ApiService;
import com.devs.frutybot.data.mapper.FruitMapper;
import com.devs.frutybot.data.util.RepositoryCallback;
import com.devs.frutybot.domain.model.FruitDomain;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FruitRepository {
    private final ApiService apiService = ApiClient.getApiService();

    // Método para obtener frutas por departamento (usa DTO Fruit y lo convierte a FruitDomain)
    public void getFruitsByDepartment(String depto, final RepositoryCallback<List<FruitDomain>> callback) {
        apiService.getFruitsByDepartment(depto).enqueue(new Callback<List<Fruit>>() {
            @Override
            public void onResponse(Call<List<Fruit>> call, Response<List<Fruit>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<FruitDomain> fruits = FruitMapper.toDomainList(response.body());
                    callback.onSuccess(fruits);
                } else {
                    callback.onError(new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Fruit>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void uploadPhoto(File photo, String text, final RepositoryCallback<UploadResponseStart> callback) {
        RequestBody requestFile = RequestBody.create(
                MediaType.parse("image/jpeg"),
                photo
        );
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", photo.getName(), requestFile);

        RequestBody textBody = RequestBody.create(
                MediaType.parse("text/plain"),
                text
        );

        apiService.uploadFruitImage(body, textBody).enqueue(new Callback<UploadResponseStart>() {
            @Override
            public void onResponse(Call<UploadResponseStart> call, Response<UploadResponseStart> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<UploadResponseStart> call, Throwable t) {
                callback.onError(t);
            }
        });
    }


    public void searchFruits(String query, final RepositoryCallback<List<FruitDomain>> callback) {
        apiService.searchFruits(query).enqueue(new Callback<List<Fruit>>() {
            @Override
            public void onResponse(Call<List<Fruit>> call, Response<List<Fruit>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<FruitDomain> fruits = FruitMapper.toDomainList(response.body());
                    callback.onSuccess(fruits);
                } else {
                    callback.onError(new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Fruit>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }


}
