package com.example.cumulora.data.remote

import com.example.cumulora.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.cumulora.BuildConfig


object WeatherApiClient {

private val apiKey = BuildConfig.WEATHER_API_KEY

private val client by lazy {
    OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor(apiKey))
        .build()
}

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherService: WeatherService by lazy {
        retrofit.create(WeatherService::class.java)
    }

}
