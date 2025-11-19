package com.devs.frutybot.di;

import com.devs.frutybot.common.Config;
import com.devs.frutybot.data.remote.ApiClient;
import com.devs.frutybot.data.remote.ApiService;
import com.devs.frutybot.data.repository.FruitRepository;
import com.devs.frutybot.data.ws.WsManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    @Singleton
    public WsManager provideWsManager() {
        // Solo devolvemos la instancia. La conexión la gestionará MainActivity.
        return new WsManager();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Config.BASE_URL + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    public FruitRepository provideFruitRepository(ApiService apiService) {
        return new FruitRepository(apiService);
    }
}
