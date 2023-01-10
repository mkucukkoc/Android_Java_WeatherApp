package com.example.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI
{
    @GET("weather?appid=107a5f6d076b2a60efaa42792b2c299d&units=metric")
    Call<OpenWeatherMap> getWeatherWithLocation(@Query("lat")double lat,@Query("lon") double lon);

    @GET("weather?appid=107a5f6d076b2a60efaa42792b2c299d&units=metric")
    Call<OpenWeatherMap> getWeatherWithCityName(@Query("q")String name);
}
