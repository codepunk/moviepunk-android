package com.codepunk.moviepunk.data.local.typeconverter

import androidx.room.TypeConverter
import com.codepunk.moviepunk.data.local.entity.ImageSizeType

class ImageSizeTypeTypeConverter {

    // region Methods

    @TypeConverter
    fun imageSizeTypeToString(input: ImageSizeType?): String? = input?.value

    @TypeConverter
    fun stringToImageSizeType(input: String?): ImageSizeType? =
        input?.let { ImageSizeType.of(it) }

    // endregion Methods

}