package com.codepunk.moviepunk.domain.model

data class CuratedContentItem(
    val id: Int = 0,
    val label: String = "",
    val type: CuratedContentType = CuratedContentType.UNKNOWN,
    val href: String = "",
    val url: String = ""
)
