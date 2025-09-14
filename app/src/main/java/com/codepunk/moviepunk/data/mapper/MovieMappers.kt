package com.codepunk.moviepunk.data.mapper

import com.codepunk.moviepunk.data.local.entity.MovieEntity
import com.codepunk.moviepunk.data.local.relation.MovieWithGenres
import com.codepunk.moviepunk.data.remote.response.MovieResponse
import com.codepunk.moviepunk.domain.model.Movie

fun MovieResponse.toEntity(): MovieEntity = MovieEntity(
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

fun MovieResponse.toRelation(): MovieWithGenres = MovieWithGenres(
    movie = toEntity(),
    genres = genreIds.map { id -> id.toEntity(isMovieGenre = true) }
)

fun MovieWithGenres.toDomain(): Movie = Movie(
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
