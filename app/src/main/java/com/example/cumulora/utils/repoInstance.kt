package com.example.cumulora.utils

import android.content.Context
import com.example.cumulora.data.local.AppDataBase
import com.example.cumulora.data.local.WeatherLocalDataSourceImpl
import com.example.cumulora.data.remote.WeatherApiClient
import com.example.cumulora.data.remote.WeatherRemoteDataSourceImpl
import com.example.cumulora.data.repository.WeatherRepository
import com.example.cumulora.data.repository.WeatherRepositoryImpl


fun repoInstance(ctx: Context): WeatherRepository {
    return WeatherRepositoryImpl.getInstance(
        WeatherRemoteDataSourceImpl(WeatherApiClient.weatherService),
        WeatherLocalDataSourceImpl.getInstance(AppDataBase.getInstance(ctx).weatherDao())
    )
}