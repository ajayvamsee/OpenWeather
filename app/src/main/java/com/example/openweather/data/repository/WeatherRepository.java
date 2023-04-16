package com.example.openweather.data.repository;

import static com.example.openweather.util.AppConstants.API_KEY;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.openweather.data.model.WeatherData;
import com.example.openweather.data.remote.retrofit.OpenWeatherApi;
import com.example.openweather.service.OpenWeatherService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {

    public String TAG = WeatherRepository.class.getSimpleName();

    final MutableLiveData<WeatherData> weatherLiveData = new MutableLiveData<>();

    final MutableLiveData<WeatherData> weatherDataByCityAndCountry = new MutableLiveData<>();

    final MutableLiveData<WeatherData> weatherDataByCityAndCountryCode = new MutableLiveData<>();

    private OpenWeatherService openWeatherService;

    private OpenWeatherApi openWeatherApi;

    public WeatherRepository() {
        openWeatherService = OpenWeatherApi.createService();
    }

    public MutableLiveData<WeatherData> getWeatherDataByCityAndCountry() {
        return weatherDataByCityAndCountry;
    }

    public MutableLiveData<WeatherData> getWeatherDataByCityAndCountryCode() {
        return weatherDataByCityAndCountryCode;
    }

    public LiveData<WeatherData> getWeatherLiveData() {
        return weatherLiveData;
    }

    public void getWeatherDataByCityName(String cityName) {
        Log.d(TAG, "getWeatherDataByCityName: " + cityName);
        openWeatherService.getWeatherDataByCityName(cityName, API_KEY, "metric")
                .enqueue(new Callback<WeatherData>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherData> call, @NonNull Response<WeatherData> response) {
                        if (response.isSuccessful()) {
                            weatherLiveData.postValue(response.body());
                            Log.d(TAG, "onResponse: getWeatherDataByCityName " + response.body());
                        } else {
                            // Handle error
                            Log.d(TAG, "onResponse: Handle error");
                            weatherLiveData.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<WeatherData> call, Throwable t) {
                        // Handle error
                    }
                });


    }

    public void getWeatherDataByCityAndCountry(String cityName, String countryCode) {

        openWeatherService.getWeatherDataByCityAndCountry(cityName + "," + countryCode, API_KEY, "metric")
                .enqueue(new Callback<WeatherData>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherData> call, @NonNull Response<WeatherData> response) {
                        if (response.isSuccessful()) {
                            weatherDataByCityAndCountry.setValue(response.body());
                            Log.d(TAG, "onResponse: getWeatherDataByCityName " + response.body());
                        } else {
                            // Handle error
                            weatherDataByCityAndCountry.setValue(null);
                            Log.d(TAG, "onResponse:  Handle error");
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherData> call, Throwable t) {
                        // Handle error
                    }
                });
    }

    public void getWeatherDataByCityStateAndCountry(String cityName, String stateCode, String countryCode) {
        openWeatherService.getWeatherDataByCityStateAndCountry(cityName + "," + stateCode + "," + countryCode, API_KEY, "metric")
                .enqueue(new Callback<WeatherData>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherData> call, @NonNull Response<WeatherData> response) {
                        if (response.isSuccessful()) {
                            weatherDataByCityAndCountryCode.setValue(response.body());
                            Log.d(TAG, "onResponse: getWeatherDataByCityName " + response.body());
                        } else {
                            // Handle error
                            weatherDataByCityAndCountry.setValue(null);
                            Log.d(TAG, "onResponse:  Handle error");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<WeatherData> call, Throwable t) {
                        // Handle error
                    }
                });
    }
}
