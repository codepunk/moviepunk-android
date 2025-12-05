package com.codepunk.moviepunk.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.codepunk.moviepunk.data.local.entity.GenreEntity
import com.codepunk.moviepunk.data.local.entity.GenreMediaTypeEntity

data class GenreWithMediaTypes(
    @Embedded
    val genre: GenreEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "genre_id"
    )
    val mediaTypes: List<GenreMediaTypeEntity>
)
