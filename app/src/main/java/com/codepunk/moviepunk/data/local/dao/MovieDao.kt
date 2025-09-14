package com.codepunk.moviepunk.data.local.dao

import androidx.room.*
import com.codepunk.moviepunk.data.local.entity.MovieEntity
import com.codepunk.moviepunk.data.local.relation.MovieWithGenres

@Dao
abstract class MovieDao {

    // region Methods

    // ====================
    // Insert
    // ====================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(movie: MovieEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(movies: List<MovieEntity>): List<Long>

    // ====================
    // Query
    // ====================

    @Query("SELECT * FROM movie WHERE id = :id")
    abstract suspend fun getMovieById(id: Long): MovieEntity?

    @Query("Select * FROM movie WHERE id IN (:ids)")
    abstract suspend fun getMoviesByIds(ids: List<Long>): List<MovieEntity>

    @Transaction
    @Query("SELECT * FROM movie WHERE id = :id")
    abstract suspend fun getMovieWithGenresById(id: Long): MovieWithGenres?

    @Transaction
    @Query("SELECT * FROM movie WHERE id IN (:ids)")
    abstract suspend fun getMoviesWithGenresByIds(ids: List<Long>): List<MovieWithGenres>

    // ====================
    // Delete
    // ====================

    // endregion Methods

}
