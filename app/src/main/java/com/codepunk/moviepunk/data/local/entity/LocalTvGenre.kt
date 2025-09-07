package com.codepunk.moviepunk.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "tv_genre",
    foreignKeys = [
        ForeignKey(
            entity = LocalGenre::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = CASCADE
        )
    ]
)
data class LocalTvGenre(

    @PrimaryKey(autoGenerate = false)
    val id: Int

)
