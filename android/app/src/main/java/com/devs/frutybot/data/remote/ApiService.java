package com.devs.frutybot.data.remote;

import com.devs.frutybot.data.dto.FruitDto;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {
    // GET http://10.0.2.2:3020/frutas/depto?depto=junin

    @GET("frutas/depto")
    Call<List<FruitDto>> getFruitsByDepartment(@Query("depto") String departamento);

    @Multipart
    @POST("fruits/upload")
    Call<ResponseBody> uploadFruitImage(
            @Part MultipartBody.Part file,
            @Part("text") RequestBody text
    );
}
