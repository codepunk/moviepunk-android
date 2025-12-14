package com.codepunk.moviepunk.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate
import kotlin.time.Clock
import kotlin.time.Instant

@Entity(
    tableName = "movie"
)
data class MovieEntity(
    @PrimaryKey
    val id: Long = 0,

    val adult: Boolean = false,

    @ColumnInfo("backdrop_path")
    val backdropPath: String = "",

    val title: String = "",

    @ColumnInfo("original_language")
    val originalLanguage: String = "",

    @ColumnInfo("original_title")
    val originalTitle: String = "",

    val overview: String = "",

    @ColumnInfo("poster_path")
    val posterPath: String = "",

    @ColumnInfo("media_type")
    val mediaType: String = "",

    val popularity: Double = 0.0,

    @ColumnInfo("release_date")
    val releaseDate: LocalDate? = null,

    val video: Boolean = false,

    @ColumnInfo("vote_average")
    val voteAverage: Double = 0.0,

    @ColumnInfo("vote_count")
    val voteCount: Int = 0,

    @ColumnInfo(name = "created_at")
    val createdAt: Instant = Clock.System.now(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: Instant = createdAt
)
