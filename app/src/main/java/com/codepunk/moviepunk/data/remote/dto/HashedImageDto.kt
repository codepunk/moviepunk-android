package com.codepunk.moviepunk.data.remote.dto

data class HashedImageDto(
    val hash: String = "",
    val imageUrls: Map<Int, String> = emptyMap()
)
