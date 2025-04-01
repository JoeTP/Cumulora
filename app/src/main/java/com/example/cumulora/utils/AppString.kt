package com.example.cumulora.utils

//!DATABASE
const val DATABASE_NAME = "weather_db"
const val FAVORITE_TABLE_NAME = "favorite_table"
const val ALARMS_TABLE = "alarms_table"
const val HOME_CACHE_TABLE = "home_cache_table"

//!Prefs
const val SHARED_PREF_NAME = "app_prefs"
const val IS_FIRST_TIME_SK = "is_first_time"
const val LAST_LON = "lastLon"
const val LAST_LAT = "lastLat"
const val LOCATION_TYPE = "locationType"
const val DEFAULT_UNITS = "metric"
var CURRENT_LANG = ""




//!Remote
const val BASE_URL = "https://api.openweathermap.org/"
const val uriWeather = "data/2.5"
const val uriGeo = "geo/1.0"
const val WEATHER_EP = "$uriWeather/weather"
const val FORECAST_EP = "$uriWeather/forecast"
const val GEOCODER_EP = uriGeo
const val API_KEY_Q = "appid"
const val LAT = "lat"
const val LON = "lon"
const val LANG = "lang"
const val UNITS = "units"
const val CNT = "cnt"
const val Q = "q"
const val LIMIT = "limit"


//val UNIT_TYPE = listOf("standard", "metric", "imperial")

//!Worker IDs
const val ALARM_ID_KEY = "alarm_id"
const val ALARM_LABEL_KEY = "alarm_label"
const val DURATION_KEY = "duration_minutes"
const val ALARM_CHANNEL_ID = "alarm_channel"
