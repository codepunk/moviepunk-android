package com.codepunk.moviepunk.data.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.codepunk.moviepunk.data.local.entity.GenreEntity
import com.codepunk.moviepunk.data.local.entity.MovieEntity
import com.codepunk.moviepunk.data.local.entity.MovieGenreXefEntity

data class MovieWithGenres(
    @Embedded
    val movie: MovieEntity = MovieEntity(),
    @Relation(
        entity = GenreEntity::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = MovieGenreXefEntity::class,
            parentColumn = "movie_id",
            entityColumn = "genre_id"
        )
    )
    val genres: List<GenreWithMediaTypes> = emptyList()
)
