package com.tahadroid.newsapp.data.remote

import com.tahadroid.newsapp.pojo.headlines.HeadlinesResponse
import com.tahadroid.newsapp.pojo.sources.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
@GET("sources")
    fun getSources(@Query("apiKey") apiKey:String) :Call<SourcesResponse>
    @GET("top-headlines")
    fun getHeadlines(
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String
    ): Call<HeadlinesResponse>


    @GET("everything")
    fun searchAbout(
        @Query("q")q:String,
        @Query("apiKey") apiKey:String
    ): Call<HeadlinesResponse>
}