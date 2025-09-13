package com.codepunk.moviepunk.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.codepunk.moviepunk.data.local.entity.LocalMovieGenreCrossRef

@Dao
interface CrossRefDao {

    // region Methods

    // ====================
    // Insert
    // ====================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(crossRefs: List<LocalMovieGenreCrossRef>)

    // endregion Methods

}