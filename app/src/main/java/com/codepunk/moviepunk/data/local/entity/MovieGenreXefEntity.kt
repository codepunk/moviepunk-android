package com.codepunk.moviepunk.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "movie_genre_xref",
    primaryKeys = ["movie_id", "genre_id"],
    indices = [
        Index("movie_id"),
        Index("genre_id")
    ],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GenreEntity::class,
            parentColumns = ["id"],
            childColumns = ["genre_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class MovieGenreXefEntity(
    @ColumnInfo(name = "movie_id")
    val movieId: Long,
    @ColumnInfo(name = "genre_id")
    val genreId: Int
)