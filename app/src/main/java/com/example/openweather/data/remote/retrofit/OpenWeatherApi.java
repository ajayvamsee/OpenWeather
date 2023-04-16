package com.example.openweather.data.remote.retrofit;

import static com.example.openweather.util.AppConstants.BASE_URL;

import com.example.openweather.service.OpenWeatherService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenWeatherApi {

    public static OpenWeatherService createService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(OpenWeatherService.class);
    }
}
