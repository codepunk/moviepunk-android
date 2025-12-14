package com.codepunk.moviepunk.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codepunk.moviepunk.data.local.entity.TrendingMovieRemoteKeyEntity

@Dao
interface TrendingMovieRemoteKeyDao {

    // region Methods

    // ====================
    // Insert
    // ====================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<TrendingMovieRemoteKeyEntity>)

    // ====================
    // Query
    // ====================

    // Query to get a specific key by item ID (useful for REFRESH anchor position)
    @Query("""
        SELECT * 
          FROM trending_movie_remote_key 
         WHERE movie_id = :movieId
    """)
    suspend fun getByMovieId(movieId: Long): TrendingMovieRemoteKeyEntity?

    // Query to get the remote key for the *last* item loaded (useful for APPEND)
    @Query("""
          SELECT * 
            FROM trending_movie_remote_key  
        ORDER BY created_at DESC LIMIT 1
    """)
    suspend fun getLatest(): TrendingMovieRemoteKeyEntity?

    // Query to get the remote key for the *first* item loaded (useful for PREPEND)
    @Query("""
          SELECT * 
            FROM trending_movie_remote_key 
        ORDER BY created_at ASC LIMIT 1
    """)
    suspend fun getEarliest(): TrendingMovieRemoteKeyEntity?

    // ====================
    // Delete
    // ====================

    @Query("DELETE FROM trending_movie_remote_key")
    suspend fun clearAll()

    // endregion Methods

}
