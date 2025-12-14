package com.codepunk.moviepunk.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Clock
import kotlin.time.Instant

@Entity(tableName = "trending_movie_remote_key")
data class TrendingMovieRemoteKeyEntity(
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val movieId: Long,

    @ColumnInfo(name = "prev_key")
    val prevKey: Int? = null,

    @ColumnInfo(name = "next_key")
    val nextKey: Int? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: Instant = Clock.System.now()
)
