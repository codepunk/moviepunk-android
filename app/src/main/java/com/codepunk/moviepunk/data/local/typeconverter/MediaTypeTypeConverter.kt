package com.codepunk.moviepunk.data.local.typeconverter

import androidx.room.TypeConverter
import com.codepunk.moviepunk.domain.model.MediaType

class MediaTypeTypeConverter {

    // region Methods

    @TypeConverter
    fun localDateToString(input: MediaType?): String? = input?.value

    @TypeConverter
    fun stringToLocalDate(input: String?): MediaType? =
        input?.let { MediaType.of(it) }

    // endregion Methods

}