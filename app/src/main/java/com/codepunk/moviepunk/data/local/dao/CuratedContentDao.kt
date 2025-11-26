package com.codepunk.moviepunk.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codepunk.moviepunk.data.local.entity.CuratedContentItemEntity
import kotlin.time.Instant

@Dao
interface CuratedContentDao {

    // ====================
    // Insert
    // ====================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(curatedContent: List<CuratedContentItemEntity>)

    // ====================
    // Delete
    // ====================

    /**d
     * Deletes all data in the genre table. This should also clear the
     * movie_genre and tv_genre tables due to foreign key cascade.
     */
    @Query("DELETE FROM curated_content")
    suspend fun clearCuratedContent(): Int

    // ====================
    // Query
    // ====================

    @Query("SELECT href FROM curated_content LIMIT 1")
    suspend fun getCuratedContentHref(): String?

    @Query("SELECT * FROM curated_content ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomCuratedContentItem(): CuratedContentItemEntity?

    @Query("SELECT max(created_at) FROM curated_content")
    suspend fun getNewestCuratedContent(): Instant

    // endregion Methods

}