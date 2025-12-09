package com.codepunk.moviepunk.data.local.typeconverter

import androidx.room.TypeConverter
import com.codepunk.moviepunk.domain.model.CuratedContentType

class CuratedContentTypeTypeConverter {

    // region Methods

    @TypeConverter
    fun localDateToString(input: CuratedContentType?): String? = input?.value

    @TypeConverter
    fun stringToLocalDate(input: String?): CuratedContentType? =
        input?.let { CuratedContentType.of(it) }

    // endregion Methods

}
