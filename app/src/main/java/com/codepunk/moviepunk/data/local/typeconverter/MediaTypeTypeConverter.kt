package com.codepunk.moviepunk.data.local.typeconverter

import androidx.room.TypeConverter
import com.codepunk.moviepunk.domain.model.MediaType

class MediaTypeTypeConverter {

    // region Methods

    @TypeConverter
    fun mediaTypeToString(input: MediaType?): String? = input?.value

    @TypeConverter
    fun stringToMediaType(input: String?): MediaType? =
        input?.let { MediaType.of(it) }

    // endregion Methods

}
