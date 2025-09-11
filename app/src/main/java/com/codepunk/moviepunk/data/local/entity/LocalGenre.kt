package com.codepunk.moviepunk.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Clock
import kotlin.time.Instant

@Entity(tableName = "genre")
data class LocalGenre constructor(

    @PrimaryKey(autoGenerate = false)
    val id: Int,

    val name: String,

    @ColumnInfo(name = "is_movie_genre")
    val isMovieGenre: Boolean,

    @ColumnInfo(name = "is_tv_genre")
    val isTvGenre: Boolean,

    @ColumnInfo(name = "created_at")
    val createdAt: Instant = Clock.System.now(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: Instant = Clock.System.now()

)
