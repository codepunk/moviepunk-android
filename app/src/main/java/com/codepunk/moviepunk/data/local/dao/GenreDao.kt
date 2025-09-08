package com.codepunk.moviepunk.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.codepunk.moviepunk.data.local.entity.LocalGenre
import com.codepunk.moviepunk.data.local.entity.LocalMovieGenre
import com.codepunk.moviepunk.data.local.entity.LocalTvGenre
import kotlin.time.Instant

@Dao
abstract class GenreDao {

    // region Methods

    // ====================
    // Insert
    // ====================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertGenres(
        genres: List<LocalGenre>
    )

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertMovieGenres(
        movieGenres: List<LocalMovieGenre>
    )

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertTvGenres(
        tvGenres: List<LocalTvGenre>
    )

    @Transaction
    open suspend fun insertGenres(
        genres: List<LocalGenre>,
        movieGenres: List<LocalMovieGenre>,
        tvGenres: List<LocalTvGenre>
    ) {
        clearGenres()
        insertGenres(genres)
        insertMovieGenres(movieGenres)
        insertTvGenres(tvGenres)
    }

    // ====================
    // Delete
    // ====================

    /**
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
    abstract suspend fun getGenres(): List<LocalGenre>

    @Query("""
        SELECT * 
        FROM genre
        INNER JOIN movie_genre 
        ON genre.id = movie_genre.id
    """)
    abstract suspend fun getMovieGenres(): List<LocalGenre>

    @Query("""
        SELECT * 
        FROM genre
        INNER JOIN tv_genre 
        ON genre.id = tv_genre.id
    """)
    abstract suspend fun getTvGenres(): List<LocalGenre>

    @Query("SELECT max(created_at) FROM genre")
    abstract suspend fun getNewestGenre(): Instant

    // endregion Methods

}
