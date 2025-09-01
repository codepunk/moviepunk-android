package com.codepunk.moviepunk.data.mapper

import com.codepunk.moviepunk.data.remote.entity.RemoteGenre
import com.codepunk.moviepunk.domain.model.Genre

@Suppress("unused")
fun RemoteGenre.toGenre() = Genre(
    id = id,
    name = name
)
