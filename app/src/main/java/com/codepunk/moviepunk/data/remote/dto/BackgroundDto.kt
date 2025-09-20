package com.codepunk.moviepunk.data.remote.dto

data class BackgroundDto(
    val hash: String,
    val name: String,
    val urls: Map<String, String>
)
