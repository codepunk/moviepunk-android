package com.codepunk.moviepunk.data.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.codepunk.moviepunk.data.local.entity.LocalGenre
import com.codepunk.moviepunk.data.local.entity.LocalMovie
import com.codepunk.moviepunk.data.local.entity.LocalMovieGenreCrossRef

data class LocalMovieWithGenres(
    @Embedded
    val movie: LocalMovie = LocalMovie(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = LocalMovieGenreCrossRef::class,
            parentColumn = "movie_id",
            entityColumn = "genre_id"
        )
    )
    val genres: List<LocalGenre> = emptyList()
)
