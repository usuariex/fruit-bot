package com.devs.frutybot.data.repository;

import com.devs.frutybot.data.dto.UploadResponse;
import com.devs.frutybot.data.remote.ApiClient;
import com.devs.frutybot.data.remote.ApiService;
import com.devs.frutybot.data.dto.FruitDto;
import com.devs.frutybot.data.mapper.FruitMapper;
import com.devs.frutybot.data.util.RepositoryCallback;
import com.devs.frutybot.domain.model.Fruit;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FruitRepository {
    private final ApiService apiService = ApiClient.getApiService();

    public void getFruitsByDepartment(String depto, final RepositoryCallback<List<Fruit>> callback) {
        apiService.getFruitsByDepartment(depto).enqueue(new Callback<List<FruitDto>>() {
            @Override
            public void onResponse(Call<List<FruitDto>> call, Response<List<FruitDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Fruit> fruits = FruitMapper.toDomainList(response.body());
                    callback.onSuccess(fruits);
                } else {
                    callback.onError(new Throwable("Error en la respuesta"));
                }
            }

            @Override
            public void onFailure(Call<List<FruitDto>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }



    public void uploadPhoto(File photo, String text, final RepositoryCallback<UploadResponse> callback) {
        RequestBody requestFile = RequestBody.create(
                okhttp3.MediaType.parse("image/jpeg"),
                photo
        );
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", photo.getName(), requestFile);

        RequestBody textBody = RequestBody.create(
                okhttp3.MediaType.parse("text/plain"),
                text
        );

        apiService.uploadFruitImage(body, textBody).enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 👈 devolvemos el objeto completo con message, requestId, status, imageUrl
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(new Throwable("Error en la respuesta: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }




}
