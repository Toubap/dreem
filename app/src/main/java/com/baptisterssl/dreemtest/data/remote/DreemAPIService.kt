package com.baptisterssl.dreemtest.data.remote

import com.baptisterssl.dreemtest.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface DreemAPIService {

    companion object {
        fun createService(url: String): DreemAPIService {
            @Suppress("ConstantConditionIf")
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .readTimeout(60, TimeUnit.SECONDS)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            level =
                                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                        })
                        .build()
                )
                .baseUrl(url)
                .build()

            return retrofit.create()
        }
    }

    @GET("/test2.json")
    suspend fun fetchAudioCarousel(): AudioFileResponseBody
}