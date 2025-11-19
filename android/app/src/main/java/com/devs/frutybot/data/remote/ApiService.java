package com.devs.frutybot.data.remote;

import com.devs.frutybot.data.dto.Fruit;
import com.devs.frutybot.data.dto.UploadResponseStart;


import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    @GET("api/frutas/depto")
    Call<List<Fruit>> getFruitsByDepartment(@Query("depto") String departamento);

    @Multipart
    @POST("api/frutas/recibirImg")
    Call<UploadResponseStart> uploadFruitImage(
            @Part MultipartBody.Part file,
            @Part("text") RequestBody text
    );




   /* @GET("api/frutas/buscar")
    Call<List<FruitDto>> buscarFrutas(
            @Query("nombre") String nombre
    );*/


    @GET("api/frutas/buscar")
    Call<List<Fruit>> searchFruits(@Query("q") String query);

}

