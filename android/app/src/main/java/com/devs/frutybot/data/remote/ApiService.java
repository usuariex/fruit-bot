package com.devs.frutybot.data.remote;

import com.devs.frutybot.data.dto.FruitDto;
import com.devs.frutybot.data.dto.UploadResponse;

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
    Call<List<FruitDto>> getFruitsByDepartment(@Query("depto") String departamento);

    @Multipart
    @POST("api/frutas/recibirImg")
    Call<UploadResponse> uploadFruitImage(
            @Part MultipartBody.Part file,
            @Part("text") RequestBody text
    );
    @GET("fruta/buscar")
    Call<List<FruitDto>> buscarFrutas(
            @Query("nombre") String nombre
    );

}

