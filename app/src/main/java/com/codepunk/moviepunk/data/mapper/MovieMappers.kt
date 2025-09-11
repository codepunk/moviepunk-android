package com.codepunk.moviepunk.data.mapper

import com.codepunk.moviepunk.data.local.entity.LocalMovie
import com.codepunk.moviepunk.data.local.relation.LocalMovieWithGenreIds
import com.codepunk.moviepunk.data.local.relation.LocalMovieWithGenres
import com.codepunk.moviepunk.data.remote.entity.RemoteGenre
import com.codepunk.moviepunk.data.remote.entity.RemoteMovie

fun RemoteMovie.toLocalMovie(): LocalMovie = LocalMovie(
    id = id,
    adult = adult,
    backdropPath = backdropPath,
    title = title,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    posterPath = posterPath,
    mediaType = mediaType,
    popularity = popularity,
    releaseDate = releaseDate,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount
)

fun RemoteMovie.toLocalMovieWithGenreId(): LocalMovieWithGenreIds = LocalMovieWithGenreIds(
    movie = toLocalMovie(),
    genreIds
)
