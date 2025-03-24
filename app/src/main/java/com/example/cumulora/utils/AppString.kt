package com.example.cumulora.utils

//!Local
const val DATABASE_NAME = "weather_db"
const val SHARED_PREF_NAME = "sharedPref"


//!Remote
const val BASE_URL = "https://api.openweathermap.org/"
const val uriWeather = "data/2.5"
const val uriGeo = "geo/1.0"
const val WEATHER_EP = "$uriWeather/weather"
const val FORECAST_EP = "$uriWeather/forecast"
const val GEOCODER_EP = uriGeo
//const val API_KEY_Q = "appid"
const val LAT = "lat"
const val LON = "lon"
const val LANG = "lang"
const val UNIT = "lon"
const val CNT = "cnt"
const val Q = "q"
const val LIMIT = "limit"


val UNIT_TYPE = listOf("standard", "metric", "imperial")

//!DataStoreKeys
//? "SK" for String Key
const val DATA_STORE_NAME = "data_store"
const val IS_FIRST_TIME_SK = "is_first_time"
const val LANG_SK = "lang"
const val LANG_EN = "en"
const val LANG_AR = "ar"
const val USE_CURRENT_LOC_SK = "use_current_loc"