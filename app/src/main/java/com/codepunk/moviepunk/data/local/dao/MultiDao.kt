package com.codepunk.moviepunk.data.local.dao

import androidx.room.Transaction
import com.codepunk.moviepunk.data.local.entity.LocalMovieGenreCrossRef
import com.codepunk.moviepunk.data.local.relation.LocalMovieWithGenreIds
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
    suspend fun insertMovie(movieWithGenreIds: LocalMovieWithGenreIds) {
        val movieId = movieDao.insertMovie(movieWithGenreIds.movie)
        crossRefDao.insertMoveGenreCrossRefs(
            movieWithGenreIds.genreIds.map { genreId ->
                LocalMovieGenreCrossRef(
                    movieId = movieId,
                    genreId = genreId
                )
            }
        )
    }

    @Transaction
    suspend fun insertMovies(moviesWithGenreIds: List<LocalMovieWithGenreIds>) {
        movieDao.insertMovies(moviesWithGenreIds.map { it.movie })
        crossRefDao.insertMoveGenreCrossRefs(
            moviesWithGenreIds.flatMap { movieWithGenreIds ->
                movieWithGenreIds.genreIds.map { genreId ->
                    LocalMovieGenreCrossRef(
                        movieId = movieWithGenreIds.movie.id,
                        genreId = genreId
                    )
                }
            }
        )
    }

    // endregion Methods

}