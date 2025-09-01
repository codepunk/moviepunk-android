package com.codepunk.moviepunk.data.remote.webservice

import arrow.retrofit.adapter.either.ResponseE
import com.codepunk.moviepunk.data.remote.entity.RemoteApiError
import com.codepunk.moviepunk.data.remote.entity.RemoteMoviePage
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviePunkWebservice {

    @GET("trending/movie/day")
    suspend fun fetchTrendingMovies(
        @Query("language") language: String = "en-US",
    ): ResponseE<RemoteApiError, RemoteMoviePage>

    // TODO Figure out a way to make this error gracefully
    @Suppress("unused")
    @GET("//trending/movie/day")
    suspend fun fetchTrendingMovies404(
        @Query("language") language: String = "en-US",
    ): ResponseE<RemoteApiError, RemoteMoviePage>

}
