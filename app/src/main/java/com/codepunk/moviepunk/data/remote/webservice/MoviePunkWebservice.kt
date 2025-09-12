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
    suspend fun fetchMovieGenres(): Response<RemoteGenreListResult>

    @GET("genre/tv/list")
    suspend fun fetchTvGenres(): Response<RemoteGenreListResult>

    // ====================
    // Trending
    // ====================

    @GET("trending/movie/{time_window}")
    suspend fun fetchTrendingMovies(
        @Path("time_window") timeWindow: String = "day",
        @Query("page") page: Int = 1
    ): Response<RemoteMoviePage>

    // endregion Methods

}
