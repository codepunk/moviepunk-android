package com.codepunk.moviepunk.data.local.typeconverter

import androidx.room.TypeConverter
import kotlin.time.Instant

class InstantTypeConverter {

    @TypeConverter
    fun instantToString(input: Instant): String = input.toString()

    @TypeConverter
    fun stringToInstant(input: String): Instant =
        try {
            Instant.parse(input)
        } catch (_: IllegalArgumentException) {
            Instant.DISTANT_PAST
        }

}
