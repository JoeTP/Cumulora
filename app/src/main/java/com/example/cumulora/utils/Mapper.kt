import com.example.cumulora.data.models.forecast.Forecast
import com.example.cumulora.data.models.forecast.ForecastEntity
import com.example.cumulora.data.models.forecast.ForecastResponse
import com.example.cumulora.data.models.weather.WeatherEntity
import com.example.cumulora.data.models.weather.WeatherResponse
import java.text.SimpleDateFormat
import java.util.TimeZone

fun WeatherResponse.toFinalWeather(): WeatherEntity {
    val currentTemp = this.main.temp
    val feelsLike = this.main.feelsLike
    val humidity = this.main.humidity
    val windSpeed = this.wind.speed
    val windDegree = this.wind.deg
    val pressure = this.main.pressure
    val clouds = this.clouds.all
    val city = this.name
    val icon = this.weatherList.firstOrNull()?.icon ?: ""
    val description = this.weatherList.firstOrNull()?.description ?: ""

    val epochSeconds = this.dt
    val timezoneOffsetSeconds = this.timezone
    val localTimeMillis = (epochSeconds + timezoneOffsetSeconds) * 1000

    val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    val timeFormat = SimpleDateFormat("HH:mm:ss")
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    timeFormat.timeZone = TimeZone.getTimeZone("UTC")

    val currentDate = dateFormat.format(localTimeMillis)
    val currentTime = timeFormat.format(localTimeMillis)

    return WeatherEntity(
        currentTemp = currentTemp,
        feelsLike = feelsLike,
        currentDate = currentDate,
        currentTime = currentTime,
        humidity = humidity,
        windSpeed = windSpeed,
        windDegree = windDegree,
        pressure = pressure,
        clouds = clouds,
        city = city,
        icon = icon,
        description = description
    )
}
//
//fun ForecastResponse.toFinalForecast(): ForecastEntity {
//    val dt = this.forecastList.dt
//    val dtTxt = this.dtTxt
//    val temp = this.main.temp
//    val tempMax = this.main.tempMax
//    val tempMin = this.main.tempMin
//    val feelsLike = this.main.feelsLike
//    val humidity = this.main.humidity
//    val windSpeed = this.wind.speed
//    val windDegree = this.wind.deg
//    val pressure = this.main.pressure
//    val clouds = this.clouds.all
//    val icon = this.weather.first().icon
//    val description = this.weather.first().description
//    val main = this.weather.first().main
//
//    return ForecastEntity(
//        dt = dt,
//        dtTxt = dtTxt,
//        main = main,
//        clouds = clouds,
//        temp = temp,
//        tempMax = tempMax,
//        tempMin = tempMin,
//        feelsLike = feelsLike,
//        humidity = humidity,
//        windSpeed = windSpeed,
//        windDegree = windDegree,
//        pressure = pressure,
//        icon = icon,
//        description = description,
//    )
//}
