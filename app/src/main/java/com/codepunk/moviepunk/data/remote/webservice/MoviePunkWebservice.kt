package com.codepunk.moviepunk.data.remote.webservice

import com.codepunk.moviepunk.data.remote.entity.RemoteGenreListResult
import com.codepunk.moviepunk.data.remote.entity.RemoteMoviePage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface MoviePunkWebservice {

    // region Methods

    // ====================
    // Genres
    // ====================

    @GET("genre/movie/list")
    suspend fun fetchMovieGenres(
        @Query("language") language: String = "en-US", // TODO Clear all of these "en-us" references
    ): Response<RemoteGenreListResult>

    @GET("genre/tv/list")
    suspend fun fetchTvGenres(
        @Query("language") language: String = "en-US",
    ): Response<RemoteGenreListResult>

    // ====================
    // Trending
    // ====================

    @GET("trending/movie/{time_window}")
    suspend fun fetchTrendingMovies(
        @Path("time_window") timeWindow: String = "day",
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
    ): Response<RemoteMoviePage>

    // endregion Methods

}
