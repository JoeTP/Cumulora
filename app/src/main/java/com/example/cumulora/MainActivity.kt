package com.example.cumulora

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.cumulora.data.remote.WeatherApiClient
import com.example.cumulora.data.remote.WeatherRemoteDataSourceImpl
import com.example.cumulora.data.repository.WeatherRepository
import com.example.cumulora.data.repository.WeatherRepositoryImpl
import com.example.cumulora.ui.theme.CumuloraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GetWeather(MainActivityVM(getRepoInstance(this)))
        setContent {
            CumuloraTheme {
                Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                }

            }
        }
    }
}

fun GetWeather(viewModel: MainActivityVM) {
    val productList = viewModel.getWeather(40.40, 40.40)
    Log.i("TAG", "getWeather: ${productList}")
}

fun getRepoInstance(ctx: Context): WeatherRepository {
    return WeatherRepositoryImpl.getInstance(
        WeatherRemoteDataSourceImpl(WeatherApiClient.weatherService),
        /*WeatherLocalDataSourceImpl.getInstance(AppDataBase.getInstance(ctx).weatherDao())*/
    )
}



