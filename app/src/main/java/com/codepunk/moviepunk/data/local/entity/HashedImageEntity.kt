package com.codepunk.moviepunk.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "hashed_image",
    primaryKeys = ["hash", "id", "density"],
)
data class HashedImageEntity(
    val hash: String,
    val id: Int,
    val density: Int,
    @ColumnInfo(name = "url_string")
    val urlString: String
)
