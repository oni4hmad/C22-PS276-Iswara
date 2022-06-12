package com.example.iswara.data.network

import com.example.iswara.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// real endpoint: https://iswara-project.as.r.appspot.com/

class ApiConfig {
    companion object {
        const val ALL_CERITA_URL = "https://62a42647259aba8e10e317a7.mockapi.io/"
        const val USER_CERITA_URL = "https://62a4643947e6e400639196cd.mockapi.io/"

        fun getApiService(base_url: String): ApiService {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}