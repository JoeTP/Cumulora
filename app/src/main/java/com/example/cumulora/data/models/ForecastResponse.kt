package com.example.cumulora.data.models

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName(value = "list")
    val forecastList: List<Forecast>
)







