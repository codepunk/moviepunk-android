package com.codepunk.moviepunk.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.codepunk.moviepunk.data.local.entity.MovieGenreXefEntity

@Dao
interface MovieGenreXrefDao {

    // region Methods

    // ====================
    // Insert
    // ====================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(crossRefs: List<MovieGenreXefEntity>)

    // endregion Methods

}
