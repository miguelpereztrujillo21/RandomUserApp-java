package com.mpt.randomuserapp_java.di;

import com.mpt.randomuserapp_java.network.ApiRepository;
import com.mpt.randomuserapp_java.network.ApiRepositoryImpl;
import com.mpt.randomuserapp_java.network.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

    @Provides
    @Singleton
    public ApiService provideApi() {
        return new Retrofit.Builder()
                .baseUrl("BuildConfig.API_URL")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class);
    }

/*    @Provides
    public UserPagingSourceFactory provideUserPagingSourceFactory(ApiRepository apiRepository) {
        return new UserPagingSourceFactory(apiRepository);
    }*/

    @Provides
    @Singleton
    public ApiRepository provideApiRepository(ApiService api) {
        return new ApiRepositoryImpl(api);
    }
}