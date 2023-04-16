package com.example.openweather.service;

import com.example.openweather.data.model.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherService {

    @GET("weather")
    Call<WeatherData> getWeatherDataByCityName(@Query("q") String cityName,
                                               @Query("appid") String apiKey,
                                               @Query("units") String units);

    @GET("weather")
    Call<WeatherData> getWeatherDataByCityAndCountry(@Query("q") String cityAndCountry,
                                                     @Query("appid") String apiKey,
                                                     @Query("units") String units);

    @GET("weather")
    Call<WeatherData> getWeatherDataByCityStateAndCountry(@Query("q") String cityStateCountry,
                                                          @Query("appid") String apiKey,
                                                          @Query("units") String units);

}
