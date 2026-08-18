package com.example.di

import android.content.Context
import com.example.data.api.StaffApiService
import com.example.data.repository.SessionManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AppContainer(private val context: Context) {

    val sessionManager: SessionManager by lazy {
        SessionManager(context)
    }

    private val authInterceptor = Interceptor { chain ->
        val token = runBlocking { sessionManager.tokenFlow.firstOrNull() }
        val requestBuilder = chain.request().newBuilder()
        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }
        chain.proceed(requestBuilder.build())
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        // Connect to local Express backend running on emulator host loopback
        .baseUrl("http://10.0.2.2:3002/api/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()

    val apiService: StaffApiService by lazy {
        retrofit.create(StaffApiService::class.java)
    }
}
