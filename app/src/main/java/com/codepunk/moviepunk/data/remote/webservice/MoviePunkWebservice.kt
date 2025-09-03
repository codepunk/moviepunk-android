package com.codepunk.moviepunk.data.remote.webservice

import com.codepunk.moviepunk.data.remote.entity.RemoteMoviePage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviePunkWebservice {

    // region Methods

    @GET("trending/movie/day")
    suspend fun fetchTrendingMovies(
        @Query("language") language: String = "en-US",
    ): Response<RemoteMoviePage>

    // endregion Methods

}
