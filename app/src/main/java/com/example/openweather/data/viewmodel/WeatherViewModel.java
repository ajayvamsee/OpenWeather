package com.example.openweather.data.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.openweather.data.repository.WeatherRepository;

public class WeatherViewModel extends ViewModel {

    private WeatherRepository weatherRepository;

    public WeatherViewModel() {
        weatherRepository = new WeatherRepository();
    }

    public WeatherRepository getWeatherRepository() {
        return weatherRepository;
    }
}
