package com.codepunk.moviepunk.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.codepunk.moviepunk.domain.model.MediaType
import kotlin.time.Clock
import kotlin.time.Instant

@Entity(
    tableName = "genre_media_type",
    primaryKeys = ["genre_id", "media_type"],
    foreignKeys = [
        ForeignKey(
            entity = GenreEntity::class,
            parentColumns = ["id"],
            childColumns = ["genre_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class GenreMediaTypeEntity(
    @ColumnInfo(name = "genre_id")
    val genreId: Int,

    @ColumnInfo(name = "media_type")
    val mediaType: MediaType,

    @ColumnInfo(name = "created_at")
    val createdAt: Instant = Clock.System.now(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: Instant = createdAt
)
