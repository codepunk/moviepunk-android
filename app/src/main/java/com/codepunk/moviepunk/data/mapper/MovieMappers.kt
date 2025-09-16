package com.codepunk.moviepunk.data.mapper

import com.codepunk.moviepunk.data.local.entity.MovieEntity
import com.codepunk.moviepunk.data.local.relation.MovieWithGenres
import com.codepunk.moviepunk.data.remote.dto.MovieDto
import com.codepunk.moviepunk.domain.model.Movie

fun MovieDto.toMovieEntity(): MovieEntity = MovieEntity(
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

fun MovieDto.toMovieWithGenres(): MovieWithGenres = MovieWithGenres(
    movie = toMovieEntity(),
    genres = genreIds.map { id -> id.toGenreEntity(isMovieGenre = true) }
)

fun MovieWithGenres.toMovie(): Movie = Movie(
    id = movie.id,
    adult = movie.adult,
    backdropPath = movie.backdropPath,
    title = movie.title,
    originalLanguage = movie.originalLanguage,
    originalTitle = movie.originalTitle,
    overview = movie.overview,
    posterPath = movie.posterPath,
    mediaType = movie.mediaType,
    genres = genres.map { it.toGenre() },
    popularity = movie.popularity,
    releaseDate = movie.releaseDate,
    video = movie.video,
    voteAverage = movie.voteAverage,
    voteCount = movie.voteCount
)
