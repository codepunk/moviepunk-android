package com.codepunk.moviepunk.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codepunk.moviepunk.data.local.entity.TrendingMovieEntity

@Dao
interface TrendingMovieDao {

    // region Methods

    // ====================
    // Insert
    // ====================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(trendingMovies: List<TrendingMovieEntity>)

    // ====================
    // Delete
    // ====================

    @Query("DELETE FROM trending_movie")
    suspend fun clearAll()

    // endregion Methods

}
