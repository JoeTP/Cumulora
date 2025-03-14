package com.example.cumulora

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cumulora.data.local.AppDataBase
import com.example.cumulora.data.local.WeatherLocalDataSourceImpl
import com.example.cumulora.data.remote.WeatherApiClient
import com.example.cumulora.data.remote.WeatherRemoteDataSourceImpl
import com.example.cumulora.data.repository.WeatherRepository
import com.example.cumulora.data.repository.WeatherRepositoryImpl
import com.example.cumulora.ui.theme.CumuloraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GetWeather(MainActivityVM(getRepoInstance(this)))
        enableEdgeToEdge()
        setContent {
            CumuloraTheme {

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



