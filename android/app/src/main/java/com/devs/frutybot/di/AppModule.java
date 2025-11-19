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
        WsManager manager = new WsManager();
        // Configurar la conexión inicial aquí o dejar que la UI lo haga.
        // Para inyección global, es mejor instanciarlo.
        // La conexión se puede manejar en MainActivity o aquí si la URL es estática.
        manager.connect("ws://" + Config.BASE_URL.replace("http://", "").replace("https://", "") + "/ws");
        return manager;
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Config.BASE_URL + "/") // Asegurar slash final
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
