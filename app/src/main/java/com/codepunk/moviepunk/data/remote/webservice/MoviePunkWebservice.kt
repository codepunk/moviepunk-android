package com.codepunk.moviepunk.data.remote.webservice

import com.codepunk.moviepunk.data.remote.entity.RemoteGenreListResult
import com.codepunk.moviepunk.data.remote.entity.RemoteMoviePage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

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

    @GET("trending/movie/day")
    suspend fun fetchTrendingMovies(
        @Query("language") language: String = "en-US",
    ): Response<RemoteMoviePage>

    // endregion Methods

}
