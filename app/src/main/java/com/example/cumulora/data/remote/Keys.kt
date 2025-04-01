package com.example.cumulora.data.remote

import com.example.cumulora.utils.API_KEY_Q
import okhttp3.Interceptor
import okhttp3.Response


//const val API_KEY = BuildConfig


class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter(API_KEY_Q, apiKey)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}