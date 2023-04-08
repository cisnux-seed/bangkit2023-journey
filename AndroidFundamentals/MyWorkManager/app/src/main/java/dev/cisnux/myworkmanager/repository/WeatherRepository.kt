package dev.cisnux.myworkmanager.repository

import android.util.Log
import dev.cisnux.myworkmanager.domain.Weather
import dev.cisnux.myworkmanager.network.WeatherService
import dev.cisnux.myworkmanager.network.asWeather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(private val apiService: WeatherService) {
    suspend fun getWeatherByCity(city: String): Weather? =
        withContext(Dispatchers.IO) {
            try {
                Log.i("WeatherRepository", "getWeatherByCity")
                val weatherResponse = apiService.getWeatherByCity(city)
                weatherResponse.asWeather()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

}