package com.codepunk.moviepunk.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.codepunk.moviepunk.data.local.entity.ConfigurationItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConfigurationDao {

    // ====================
    // Insert
    // ====================

    @Insert
    suspend fun insertAll(configurationItems: List<ConfigurationItemEntity>): List<Long>

    // ====================
    // Delete
    // ====================

    @Query("DELETE FROM configuration")
    suspend fun deleteAll(): Int

    // ====================
    // Query
    // ====================

    @Query("SELECT * FROM configuration ORDER BY id")
    fun getAll(): Flow<List<ConfigurationItemEntity>>

}
