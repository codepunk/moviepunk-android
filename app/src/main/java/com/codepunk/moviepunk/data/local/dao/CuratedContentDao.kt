package com.codepunk.moviepunk.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.codepunk.moviepunk.data.local.entity.CuratedContentItemEntity
import kotlinx.coroutines.flow.Flow
import kotlin.time.Instant

@Dao
interface CuratedContentDao {

    // ====================
    // Insert
    // ====================

    @Insert
    suspend fun insertAll(curatedContent: List<CuratedContentItemEntity>): List<Long>

    // ====================
    // Delete
    // ====================

    @Query("DELETE FROM curated_content")
    suspend fun deleteAll(): Int

    // ====================
    // Query
    // ====================

    @Query("SELECT * FROM curated_content ORDER BY id")
    fun getAll(): Flow<List<CuratedContentItemEntity>>

    @Query("SELECT href FROM curated_content LIMIT 1")
    suspend fun getFirst(): String?

    @Query("SELECT min(created_at) FROM curated_content")
    suspend fun getOldest(): Instant

    @Query("""
          SELECT * 
            FROM curated_content 
           WHERE id != :currentId
        ORDER BY RANDOM() LIMIT 1
    """)
    suspend fun getRandom(currentId: Int): CuratedContentItemEntity?

    // endregion Methods

}
