package com.codepunk.moviepunk.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codepunk.moviepunk.data.local.entity.HashedImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HashedImageDao {

// region Methods

    // ====================
    // Insert
    // ====================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(hashedImages: List<HashedImageEntity>)

    // ====================
    // Delete
    // ====================

    /**
     * Deletes all data in the hashed_image table.
     */
    @Query("DELETE FROM hashed_image")
    suspend fun clearHashedImages(): Int

    // ====================
    // Query
    // ====================

    // TODO Should I make all of these return values into Flows?

    @Query("SELECT * FROM hashed_image")
    fun getHashedImages(): Flow<List<HashedImageEntity>>

    @Query("SELECT * FROM hashed_image WHERE id=:id AND density=:density")
    suspend fun getHashedImage(
        id: Int,
        density: Int
    ): List<HashedImageEntity>

    @Query("SELECT hash FROM hashed_image LIMIT 1")
    suspend fun getCurrentHash(): String?

    // endregion Methods

}
