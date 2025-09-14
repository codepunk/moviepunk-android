package com.codepunk.moviepunk.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codepunk.moviepunk.data.local.entity.GenreEntity
import kotlinx.coroutines.flow.Flow
import kotlin.time.Instant

@Dao
abstract class GenreDao {

    // region Methods

    // ====================
    // Insert
    // ====================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(genres: List<GenreEntity>)

    // ====================
    // Delete
    // ====================

    /**d
     * Deletes all data in the genre table. This should also clear the
     * movie_genre and tv_genre tables due to foreign key cascade.
     */
    @Query("DELETE FROM genre")
    abstract suspend fun clearGenres(): Int

    // ====================
    // Query
    // ====================

    // TODO Should I make these return values into Flows?

    @Query("SELECT * FROM genre")
    abstract fun getGenres(): Flow<List<GenreEntity>>

    @Query("SELECT * FROM genre WHERE is_movie_genre = 1")
    abstract suspend fun getMovieGenres(): List<GenreEntity>

    @Query("SELECT * FROM genre WHERE is_tv_genre = 1")
    abstract suspend fun getTvGenres(): List<GenreEntity>

    @Query("SELECT max(created_at) FROM genre")
    abstract suspend fun getNewestGenre(): Instant

    // endregion Methods

}
