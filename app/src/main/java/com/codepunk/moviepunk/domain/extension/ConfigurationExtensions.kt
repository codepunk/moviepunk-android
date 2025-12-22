package com.codepunk.moviepunk.domain.extension

import com.codepunk.moviepunk.domain.model.Configuration

private const val ORIGINAL_SIZE = 0
private const val ORIGINAL = "original"
private val SIZE_REGEX = "^w([0-9]+)$".toRegex()

fun Configuration.getBackdropUrl(
    path: String,
    widthPx: Int = ORIGINAL_SIZE,
    secure: Boolean = true
): String = getUrl(
    base = if (secure) images.secureBaseUrl else images.baseUrl,
    sizes = images.backdropSizes,
    widthPx = widthPx,
    path = path
)

fun Configuration.getPosterUrl(
    path: String,
    widthPx: Int = ORIGINAL_SIZE,
    secure: Boolean = true
): String = getUrl(
    base = if (secure) images.secureBaseUrl else images.baseUrl,
    sizes = images.posterSizes,
    widthPx = widthPx,
    path = path
)

private fun getUrl(
    base: String,
    sizes: List<String>,
    widthPx: Int,
    path: String,
): String = buildString {
    append(base)
    append(sizes.findWidth(widthPx))
    append(path)
}

// TODO Simplify
private fun List<String>.findWidth(widthPx: Int): String {
    val filtered = this.filter { sizeString ->
        val size = sizeString.toSize()
        size <= widthPx
    }
    val result = filtered.maxBy { sizeString ->
        sizeString.toSize()
    }
    return result
}

// TODO Simplify
private fun String.toSize(): Int {
    if (this == ORIGINAL) {
        return ORIGINAL_SIZE
    }

    val matchResult = SIZE_REGEX.matchEntire(this)
    if (matchResult != null) {
        val component = matchResult.destructured.component1()
        return component.toIntOrNull() ?: -1
    }

    return -1
}
