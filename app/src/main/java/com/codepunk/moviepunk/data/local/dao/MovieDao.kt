package com.codepunk.moviepunk.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.codepunk.moviepunk.data.local.entity.MovieEntity
import com.codepunk.moviepunk.data.local.relation.MovieWithGenres

@Dao
interface MovieDao {

    // region Methods

    // ====================
    // Insert
    // ====================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>): List<Long>

    // ====================
    // Query
    // ====================

    @Query("SELECT * FROM movie WHERE id = :id")
    suspend fun getMovieById(id: Long): MovieEntity?

    @Query("Select * FROM movie WHERE id IN (:ids)")
    suspend fun getMoviesByIds(ids: List<Long>): List<MovieEntity>

    @Transaction
    @Query("SELECT * FROM movie WHERE id = :id")
    suspend fun getMovieWithGenresById(id: Long): MovieWithGenres?

    @Transaction
    @Query("SELECT * FROM movie WHERE id IN (:ids)")
    suspend fun getMoviesWithGenresByIds(ids: List<Long>): List<MovieWithGenres>

    @Transaction
    @Query("""
        SELECT movie.*
        FROM trending_movie, movie
        WHERE trending_movie.movie_id = movie.id
    """)
    fun getTrendingMoviePagingSource(): PagingSource<Int, MovieWithGenres>

    // ====================
    // Delete
    // ====================

    // endregion Methods

}
