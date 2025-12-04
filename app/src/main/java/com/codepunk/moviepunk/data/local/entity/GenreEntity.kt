package com.codepunk.moviepunk.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Clock
import kotlin.time.Instant

@Entity(tableName = "genre")
data class GenreEntity constructor(

    @PrimaryKey(autoGenerate = false)
    val id: Int,

    val name: String = "",

    @ColumnInfo(name = "created_at")
    val createdAt: Instant = Clock.System.now(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: Instant = createdAt

)
