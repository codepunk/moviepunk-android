package com.codepunk.moviepunk.data.remote.webservice

import com.codepunk.moviepunk.data.remote.response.GenreListResponse
import com.codepunk.moviepunk.data.remote.response.MoviePageResponse
import com.codepunk.moviepunk.domain.model.MediaType
import com.codepunk.moviepunk.domain.model.TimeWindow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface MoviePunkWebservice {

    // region Methods

    // ====================
    // Genres
    // ====================

    @GET("genre/{entity_type}/list")
    suspend fun fetchGenres(
        @Path("entity_type") mediaType: MediaType
    ): Response<GenreListResponse>

    // ====================
    // Trending
    // ====================

    @GET("trending/{entity_type}/{time_window}")
    suspend fun fetchTrendingMovies(
        @Path("entity_type") mediaType: MediaType = MediaType.MOVIE,
        @Path("time_window") timeWindow: TimeWindow = TimeWindow.DAY,
        @Query("page") page: Int = 1
    ): Response<MoviePageResponse>

    // endregion Methods

}
