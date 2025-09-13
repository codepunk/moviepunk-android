package com.codepunk.moviepunk.data.mapper

import com.codepunk.moviepunk.data.local.entity.LocalGenre
import com.codepunk.moviepunk.data.local.entity.LocalMovie
import com.codepunk.moviepunk.data.local.relation.LocalMovieWithGenres
import com.codepunk.moviepunk.data.remote.entity.RemoteMovie
import com.codepunk.moviepunk.domain.model.Movie

fun RemoteMovie.toLocal(): LocalMovie = LocalMovie(
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

fun RemoteMovie.toLocalWithGenres(): LocalMovieWithGenres = LocalMovieWithGenres(
    movie = toLocal(),
    genreIds.map { genreId ->
        LocalGenre(
            id = genreId,
            isMovieGenre = true,
            isTvGenre = false
        )
    }
)

fun LocalMovieWithGenres.toDomain(): Movie = Movie(
    id = movie.id,
    adult = movie.adult,
    backdropPath = movie.backdropPath,
    title = movie.title,
    originalLanguage = movie.originalLanguage,
    originalTitle = movie.originalTitle,
    overview = movie.overview,
    posterPath = movie.posterPath,
    mediaType = movie.mediaType,
    genres = genres.map { it.toDomain() },
    popularity = movie.popularity,
    releaseDate = movie.releaseDate,
    video = movie.video,
    voteAverage = movie.voteAverage,
    voteCount = movie.voteCount
)
