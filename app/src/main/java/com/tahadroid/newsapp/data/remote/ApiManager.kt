package com.tahadroid.newsapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL ="https://newsapi.org/v2/"
object  ApiManager {
val retrofit =Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

    val apiServices = retrofit.create(ApiServices::class.java)
}