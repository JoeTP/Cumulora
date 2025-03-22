package com.example.cumulora.data.models.geocoder

data class GeocoderResponse (
    val name: String,
    val local_names: Map<String, String>?,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String?
)