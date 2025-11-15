package com.devs.frutybot.data.remote;

import com.devs.frutybot.data.dto.FruitDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("fruits")
    Call<List<FruitDto>> getFruits();
}
