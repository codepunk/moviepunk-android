package com.codepunk.moviepunk.data.local.dao

import androidx.room.Transaction
import com.codepunk.moviepunk.data.local.entity.MovieGenreXefEntity
import com.codepunk.moviepunk.data.local.relation.MovieWithGenres
import javax.inject.Inject

class MultiDao @Inject constructor(
    val genreDao: GenreDao,
    val movieDao: MovieDao,
    val movieGenreXrefDao: MovieGenreXrefDao
) {

    // region Methods

    // ====================
    // Insert
    // ====================

    // TODO insertMoveGenreCrossRefs could result in foreign key constraint failure if the
    //  genres arent in sync. Maybe handle that in repository?

    @Transaction
    suspend fun insert(movieWithGenres: MovieWithGenres) {
        val movieId = movieDao.insert(movieWithGenres.movie)
        movieGenreXrefDao.insertAll(
            movieWithGenres.genres.map { genre ->
                MovieGenreXefEntity(
                    movieId = movieId,
                    genreId = genre.id
                )
            }
        )
    }

    @Transaction
    suspend fun insertAll(moviesWithGenres: List<MovieWithGenres>) {
        movieDao.insertAll(moviesWithGenres.map { it.movie })
        movieGenreXrefDao.insertAll(
            moviesWithGenres.flatMap { movieWithGenreIds ->
                movieWithGenreIds.genres.map { genre ->
                    MovieGenreXefEntity(
                        movieId = movieWithGenreIds.movie.id,
                        genreId = genre.id
                    )
                }
            }
        )
    }

    // endregion Methods

}