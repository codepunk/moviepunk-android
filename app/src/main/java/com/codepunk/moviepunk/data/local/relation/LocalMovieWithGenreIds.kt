package com.codepunk.moviepunk.data.local.relation

import com.codepunk.moviepunk.data.local.entity.LocalMovie

data class LocalMovieWithGenreIds(
    val movie: LocalMovie = LocalMovie(),
    val genreIds: List<Int> = emptyList()
)
