package com.codepunk.moviepunk.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.codepunk.moviepunk.data.local.entity.GenreEntity
import com.codepunk.moviepunk.data.local.relation.GenreWithMediaTypes
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {

    // region Methods

    // ====================
    // Insert
    // ====================

    @Insert
    suspend fun insert(genres: GenreEntity)

    @Insert
    suspend fun insertAll(genres: List<GenreEntity>)

    // ====================
    // Delete
    // ====================

    /**d
     * Deletes all data in the genre table. This should also clear the
     * movie_genre and tv_genre tables due to foreign key cascade.
     */
    @Query("DELETE FROM genre")
    suspend fun deleteAll()

    // ====================
    // Query
    // ====================

    @Query(value = """
        SELECT genre.*
        FROM genre
        LEFT OUTER JOIN genre_media_type
        ON genre.id = genre_media_type.genre_id
    """)
    fun getAll(): Flow<List<GenreWithMediaTypes>>

    // endregion Methods

}