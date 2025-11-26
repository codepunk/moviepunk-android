package com.codepunk.moviepunk.domain.model

data class HashedImage(
    val hash: String = "",
    val imageUrls: Map<Int, String> = emptyMap()
)
