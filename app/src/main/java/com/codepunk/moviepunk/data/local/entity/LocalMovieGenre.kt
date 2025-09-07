package com.codepunk.moviepunk.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "movie_genre",
    foreignKeys = [
        ForeignKey(
            entity = LocalGenre::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = CASCADE
        )
    ]
)
data class LocalMovieGenre(

    @PrimaryKey(autoGenerate = false)
    val id: Int

)
