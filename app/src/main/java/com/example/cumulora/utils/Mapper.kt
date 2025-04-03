import com.example.cumulora.data.models.weather.WeatherEntity
import com.example.cumulora.data.models.weather.WeatherResponse
import java.text.SimpleDateFormat
import java.util.TimeZone

fun WeatherResponse.toFinalWeather(): WeatherEntity {
    val lat = this.coord.lat
    val lon = this.coord.lon
    val currentTemp = this.main.temp
    val maxTemp = this.main.tempMax
    val minTemp = this.main.tempMin
    val feelsLike = this.main.feelsLike
    val humidity = this.main.humidity
    val windSpeed = this.wind.speed
    val windDegree = this.wind.deg
    val pressure = this.main.pressure
    val clouds = this.clouds.all
    val city = this.name
    val icon = this.weatherList.firstOrNull()?.icon ?: ""
    val description = this.weatherList.firstOrNull()?.description ?: ""

    val sunRise = this.sys.sunrise
    val sunSet = this.sys.sunset

    val epochSeconds = this.dt
    val timezoneOffsetSeconds = this.timezone
    val localTimeMillis = (epochSeconds + timezoneOffsetSeconds)

    val dateFormat = SimpleDateFormat("dd, MMM")
    val timeFormat = SimpleDateFormat("HH:mm a")
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    timeFormat.timeZone = TimeZone.getTimeZone("UTC")

    val currentDate = dateFormat.format(localTimeMillis)
    val currentTime = timeFormat.format(localTimeMillis)

    return WeatherEntity(
        lat = lat,
        lon = lon,
        currentTemp = currentTemp,
        tempMax = maxTemp,
        tempMin = minTemp,
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
        sunRise = sunRise,
        sunSet = sunSet,
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
