package dev.cisnux.myworkmanager.network

import dev.cisnux.myworkmanager.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("/data/2.5/weather")
    suspend fun getWeatherByCity(
        @Query("q") city: String,
        @Query("appId") appId: String = BuildConfig.APP_ID
    ): WeatherResponse
}