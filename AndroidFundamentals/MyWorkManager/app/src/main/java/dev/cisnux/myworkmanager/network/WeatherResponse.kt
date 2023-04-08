package dev.cisnux.myworkmanager.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dev.cisnux.myworkmanager.domain.Weather
import java.text.DecimalFormat

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    @Json(name = "weather")
    val weather: List<WeatherItem>,

    @Json(name = "main")
    val main: Main,

    @Json(name = "name")
    val name: String
)

@JsonClass(generateAdapter = true)
data class WeatherItem(
    @Json(name = "description")
    val description: String,

    @Json(name = "main")
    val main: String
)

@JsonClass(generateAdapter = true)
data class Main(
    @Json(name = "temp")
    val temp: Float
)

fun WeatherResponse.asWeather(): Weather {
    val weatherItem = weather.first()
    val tempInCelsius = main.temp - 273
    val temperature = DecimalFormat("##.##").format(tempInCelsius)

    return Weather(
        title = "Current Weather in $name",
        message = "${weatherItem.main}, ${weatherItem.description} with $temperature celcius"
    )
}