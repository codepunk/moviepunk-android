package com.codepunk.moviepunk.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codepunk.moviepunk.data.local.entity.LocalMovie
import com.codepunk.moviepunk.data.local.relation.LocalMovieWithGenres

@Dao
abstract class MovieDao {

    // region Methods

    // ====================
    // Insert
    // ====================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertMovie(movie: LocalMovie): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertMovies(movies: List<LocalMovie>): List<Long>

    // ====================
    // Query
    // ====================

    @Query("SELECT * FROM movie WHERE id = :id")
    abstract suspend fun getMovieById(id: Long): LocalMovie?

    @Query("Select * FROM movie WHERE id IN (:ids)")
    abstract suspend fun getMoviesByIds(ids: List<Long>): List<LocalMovie>

    @Query("SELECT * FROM movie WHERE id = :id")
    abstract suspend fun getMovieWithGenresById(id: Long): LocalMovieWithGenres?

    @Query("Select * FROM movie WHERE id IN (:ids)")
    abstract suspend fun getMoviesWithGenresByIds(ids: List<Long>): List<LocalMovieWithGenres>

    // endregion Methods

}
