package com.codepunk.moviepunk.data.mapper

import com.codepunk.moviepunk.data.remote.entity.RemoteMovie
import com.codepunk.moviepunk.domain.model.Genre
import com.codepunk.moviepunk.domain.model.Movie

// TODO NEXT Let's start doing LocalMovie, handle genre IDs, etc.

fun RemoteMovie.toMovie(): Movie = Movie(
    adult = adult,
    backdropPath = backdropPath,
    id = id,
    title = title,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    posterPath = posterPath,
    mediaType = mediaType,
    genres = emptyList<Genre>(), // TODO genreIds.map { Genre(id = it) },
    popularity = popularity,
    releaseDate = releaseDate,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount
)
