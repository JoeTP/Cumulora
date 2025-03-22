package com.example.cumulora.data.remote

import com.example.cumulora.data.models.forecast.ForecastResponse
import com.example.cumulora.data.models.geocoder.GeocoderResponse
import com.example.cumulora.data.models.weather.WeatherResponse
import com.example.cumulora.utils.CNT
import com.example.cumulora.utils.FORECAST_EP
import com.example.cumulora.utils.GEOCODER_EP
import com.example.cumulora.utils.LANG
import com.example.cumulora.utils.LAT
import com.example.cumulora.utils.LIMIT
import com.example.cumulora.utils.LON
import com.example.cumulora.utils.Q
import com.example.cumulora.utils.UNIT
import com.example.cumulora.utils.WEATHER_EP
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherService {

    @GET(WEATHER_EP)
    suspend fun getWeather(
        @Query(LAT) lat: Double,
        @Query(LON) lon: Double,
        @Query(UNIT) unit: String?,
        @Query(LANG) lang: String?,
//        @Query(API_KEY_Q) apiKey: String = API_KEY
    ): Response<WeatherResponse>

    @GET(FORECAST_EP)
    suspend fun getForecast(
        @Query(LAT) lat: Double,
        @Query(LON) lon: Double,
        @Query(UNIT) unit: String?,
        @Query(LANG) lang: String?,
        @Query(CNT) cnt: Int = 40,
//        @Query(API_KEY_Q) apiKey: String = API_KEY
    ): Response<ForecastResponse>

    @GET(GEOCODER_EP)
    suspend fun getGeocoder(
        @Query(Q) query: String,
        @Query(LIMIT) limit: Int? = 1,
    ): Response<GeocoderResponse>

}
