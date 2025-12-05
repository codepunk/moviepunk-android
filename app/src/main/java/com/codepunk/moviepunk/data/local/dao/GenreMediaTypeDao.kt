package com.codepunk.moviepunk.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.codepunk.moviepunk.data.local.entity.GenreMediaTypeEntity

@Dao
interface GenreMediaTypeDao {

// region Methods

    // ====================
    // Insert
    // ====================

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(mediaTypes: List<GenreMediaTypeEntity>)

    // ====================
    // Delete
    // ====================

    // I might not actually need this due to cascade

    // ====================
    // Query
    // ====================

    // I might not actually need this due to relation

    // endregion Methods

}