package com.codepunk.moviepunk.data.mapper

import com.codepunk.moviepunk.data.remote.entity.RemoteMoviePage
import com.codepunk.moviepunk.domain.model.MoviePage

fun RemoteMoviePage.toMoviePage() = MoviePage(
    page = page,
    results = results.map { it.toMovie() },
    totalPages = totalPages,
    totalResults = totalResults
)
