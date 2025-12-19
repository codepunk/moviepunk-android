package com.codepunk.moviepunk.data.local.typeconverter

import androidx.room.TypeConverter
import com.codepunk.moviepunk.data.local.entity.ConfigurationKey

class ConfigurationKeyTypeConverter {

    // region Methods

    @TypeConverter
    fun configurationKeyToString(input: ConfigurationKey?): String? = input?.value

    @TypeConverter
    fun stringToConfigurationKey(input: String?): ConfigurationKey? =
        input?.let { ConfigurationKey.of(it) }

    // endregion Methods

}