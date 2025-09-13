package com.codepunk.moviepunk.data.local.dao

import androidx.room.Transaction
import com.codepunk.moviepunk.data.local.entity.LocalMovieGenreCrossRef
import com.codepunk.moviepunk.data.local.relation.LocalMovieWithGenres
import javax.inject.Inject

class MultiDao @Inject constructor(
    val genreDao: GenreDao,
    val movieDao: MovieDao,
    val crossRefDao: CrossRefDao
) {

    // region Methods

    // ====================
    // Insert
    // ====================

    // TODO insertMoveGenreCrossRefs could result in foreign key constraint failure if the
    //  genres arent in sync. Maybe handle that in repository?

    @Transaction
    suspend fun insert(movieWithGenres: LocalMovieWithGenres) {
        val movieId = movieDao.insert(movieWithGenres.movie)
        crossRefDao.insertAll(
            movieWithGenres.genres.map { genre ->
                LocalMovieGenreCrossRef(
                    movieId = movieId,
                    genreId = genre.id
                )
            }
        )
    }

    @Transaction
    suspend fun insertAll(moviesWithGenres: List<LocalMovieWithGenres>) {
        movieDao.insertAll(moviesWithGenres.map { it.movie })
        crossRefDao.insertAll(
            moviesWithGenres.flatMap { movieWithGenreIds ->
                movieWithGenreIds.genres.map { genre ->
                    LocalMovieGenreCrossRef(
                        movieId = movieWithGenreIds.movie.id,
                        genreId = genre.id
                    )
                }
            }
        )
    }

    // endregion Methods

}