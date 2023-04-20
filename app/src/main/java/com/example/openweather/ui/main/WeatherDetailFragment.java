package com.example.openweather.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.openweather.data.model.WeatherData;
import com.example.openweather.data.viewmodel.WeatherViewModel;
import com.example.openweather.databinding.FragmentWeatherDetailBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class WeatherDetailFragment extends Fragment {

    public static final String TAG = "WeatherDetailFragment";

    String iconUrl = "https://openweathermap.org/img/wn/04d@2x.png";

    private WeatherViewModel weatherViewModel;

    private FragmentWeatherDetailBinding binding;

    public WeatherDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");

        binding = FragmentWeatherDetailBinding.inflate(getLayoutInflater(), container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
        weatherViewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        binding.noWeather.setVisibility(View.VISIBLE);
        binding.weatherDetail.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");

        weatherViewModel.getWeatherRepository().getWeatherLiveData().observe(this, new Observer<WeatherData>() {
            @Override
            public void onChanged(WeatherData weatherData) {
                Log.d(TAG, "getWeatherLiveData: ");
                if (weatherData != null) {
                    binding.noWeather.setVisibility(View.GONE);
                    binding.weatherDetail.setVisibility(View.VISIBLE);

                    binding.timezone.setText(String.format("Time Zone: " + formatTimeInTimeZone(weatherData.getDt(), weatherData.getTimezone())));
                    binding.cityName.setText(String.format("City Name: %s", weatherData.getName()));
                    for (WeatherData.Weather weather : weatherData.getWeatherList()) {
                        binding.weatherDescription.setText(weather.getDescription());
                        iconUrl = iconUrl.replace("04d", weather.getIcon());
                        Glide.with(requireActivity()).load(iconUrl).into(binding.weatherIcon);
                    }
                    binding.temperature.setText(String.format("Temperature: " + weatherData.getMain().getTemp() + " kelvin"));
                    binding.humidity.setText(String.format("Humidity: " + weatherData.getMain().getHumidity() + ""));
                    binding.windSpeed.setText(String.format("Wind Speed: " + weatherData.getWind().getSpeed() + " miles/hour"));
                    binding.pressure.setText(String.format("pressure: " + weatherData.getMain().getPressure() + " hPa"));
                    binding.minimumTemperature.setText(String.format("Min Temp: " + weatherData.getMain().getTempMin()));
                    binding.maximumTemperature.setText(String.format("Max Temp: " + (weatherData.getMain().getTempMax())));
                    binding.sunrise.setText(String.format("Sunrise Time: " + formatTime(weatherData.getSys().getSunrise())));
                    binding.sunset.setText(String.format("Sunset Time: " + formatTime(weatherData.getSys().getSunset())));

                    Log.d(TAG, "__________________________________________________________________________________");
                    Log.d(TAG, "Weather data received:");
                    Log.d(TAG, "Latitude: " + weatherData.getCoord().getLat());
                    Log.d(TAG, "Longitude: " + weatherData.getCoord().getLon());
                    Log.d(TAG, "Base: " + weatherData.getBase());
                    Log.d(TAG, "Visibility: " + weatherData.getVisibility());
                    Log.d(TAG, "DT: " + weatherData.getDt());
                    Log.d(TAG, "Timezone: " + weatherData.getTimezone());
                    Log.d(TAG, "ID: " + weatherData.getId());
                    Log.d(TAG, "Name: " + weatherData.getName());
                    Log.d(TAG, "Cod: " + weatherData.getCod());
                    for (WeatherData.Weather weather : weatherData.getWeatherList()) {
                        Log.d(TAG, "Weather ID: " + weather.getId());
                        Log.d(TAG, "Weather Main: " + weather.getMain());
                        Log.d(TAG, "Weather Description: " + weather.getDescription());
                        Log.d(TAG, "Weather Icon: " + weather.getIcon());
                    }
                    Log.d(TAG, "Temperature: " + weatherData.getMain().getTemp());
                    Log.d(TAG, "Feels Like: " + weatherData.getMain().getFeelsLike());
                    Log.d(TAG, "Minimum Temperature: " + weatherData.getMain().getTempMin());
                    Log.d(TAG, "Maximum Temperature: " + weatherData.getMain().getTempMax());
                    Log.d(TAG, "Pressure: " + weatherData.getMain().getPressure());
                    Log.d(TAG, "Humidity: " + weatherData.getMain().getHumidity());
                    Log.d(TAG, "Sea Level: " + weatherData.getMain().getSeaLevel());
                    Log.d(TAG, "Ground Level: " + weatherData.getMain().getGrndLevel());
                    Log.d(TAG, "Wind Speed: " + weatherData.getWind().getSpeed());
                    Log.d(TAG, "Wind Degree: " + weatherData.getWind().getDeg());
                    Log.d(TAG, "Wind Gust: " + weatherData.getWind().getGust());
                    Log.d(TAG, "Clouds All: " + weatherData.getClouds().getAll());
                    Log.d(TAG, "Sys Type: " + weatherData.getSys().getType());
                    Log.d(TAG, "Sys ID: " + weatherData.getSys().getId());
                    Log.d(TAG, "Sys Country: " + weatherData.getSys().getCountry());
                    Log.d(TAG, "Sys Sunrise: " + weatherData.getSys().getSunrise());
                    Log.d(TAG, "Sys Sunset: " + weatherData.getSys().getSunset());
                } else {
                    Log.e(TAG, "Failed to get weather data");
                    binding.noWeather.setVisibility(View.VISIBLE);
                    binding.weatherDetail.setVisibility(View.GONE);
                }
            }
        });

    }

    // Utility methods for time stamps
    public String formatTime(long sunriseTimestamp) {
        Date sunriseDate = new Date(sunriseTimestamp * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
        return sdf.format(sunriseDate);
    }

    public String formatTimeInTimeZone(long utcTimestamp, int timezoneOffsetSeconds) {
        Date utcDate = new Date(utcTimestamp * 1000);
        TimeZone timezone = TimeZone.getTimeZone("GMT" + (timezoneOffsetSeconds >= 0 ? "+" : "-") + timezoneOffsetSeconds / 3600);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
        sdf.setTimeZone(timezone);
        return sdf.format(utcDate);
    }
}