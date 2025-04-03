package com.example.cumulora.features.onboard

import androidx.lifecycle.ViewModel
import com.example.cumulora.data.repository.WeatherRepository
import com.example.cumulora.utils.LAST_LAT
import com.example.cumulora.utils.LAST_LON

class OnboardViewModel(val repo: WeatherRepository) : ViewModel(){



    fun cacheUserLatLon(lat: Double, lon: Double ){
        repo.cacheData(LAST_LAT, lat.toString())
        repo.cacheData(LAST_LON, lon.toString())
    }

}



