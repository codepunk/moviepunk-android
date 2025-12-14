package com.codepunk.moviepunk.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.withTransaction
import com.codepunk.moviepunk.data.local.MoviePunkDatabase
import com.codepunk.moviepunk.data.local.entity.GenreEntity
import com.codepunk.moviepunk.data.local.relation.GenreWithMediaTypes
import kotlinx.coroutines.flow.Flow

@Dao
abstract class GenreDao(
    private val db: MoviePunkDatabase
) {

    // region Variables

    private val genreMediaTypeDao: GenreMediaTypeDao = db.genreMediaTypeDao()

    // endregion Variables

    // region Methods

    // ====================
    // Insert
    // ====================

    @Insert
    abstract suspend fun insert(genres: GenreEntity)

    @Insert
    abstract suspend fun insertAll(genres: List<GenreEntity>)

    suspend fun insertWithMediaTypes(genreWidthMediaTypes: GenreWithMediaTypes) {
        db.withTransaction {
            this.insert(genreWidthMediaTypes.genre)
            genreMediaTypeDao.insertAll(genreWidthMediaTypes.mediaTypes)
        }
    }

    suspend fun insertAllWithMediaTypes(genresWithMediaTypes: List<GenreWithMediaTypes>) {
        db.withTransaction {
            this.insertAll(
                genresWithMediaTypes.map { it.genre }
            )
            genreMediaTypeDao.insertAll(
                genresWithMediaTypes.map { it.mediaTypes }.flatten()
            )
        }
    }

    // ====================
    // Delete
    // ====================

    /**d
     * Deletes all data in the genre table. This should also clear the
     * movie_genre and tv_genre tables due to foreign key cascade.
     */
    @Query("DELETE FROM genre")
    abstract suspend fun deleteAll()

    // ====================
    // Query
    // ====================

    @Query(value = """
        SELECT genre.*
        FROM genre
        LEFT OUTER JOIN genre_media_type
        ON genre.id = genre_media_type.genre_id
    """)
    abstract fun getAll(): Flow<List<GenreWithMediaTypes>>

    // endregion Methods

}