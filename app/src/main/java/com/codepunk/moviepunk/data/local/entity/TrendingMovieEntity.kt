package com.codepunk.moviepunk.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Clock
import kotlin.time.Instant

@Entity(
    tableName = "trending_movie",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = androidx.room.ForeignKey.CASCADE,
            onUpdate = androidx.room.ForeignKey.CASCADE
        )
    ]
)
data class TrendingMovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("movie_id")
    val movieId: Long = 0,

    @ColumnInfo("created_at")
    val createdAt: Instant = Clock.System.now(),
)
