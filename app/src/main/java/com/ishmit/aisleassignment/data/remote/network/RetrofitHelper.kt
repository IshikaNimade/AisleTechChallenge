package com.ishmit.aisleassignment.data.remote.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitHelper {

    // Base URL of the API
    private const val BASE_URL = "https://app.aisle.co/V1/"

    // Create and configure Moshi instance with KotlinJsonAdapterFactory
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // Create Retrofit instance using the configured Moshi instance
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    /**
     * Create and return an instance of ApiService using the Retrofit instance.
     *
     * @return [ApiService] instance to make API calls.
     */
    fun create(): ApiService = retrofit.create(ApiService::class.java)
}
