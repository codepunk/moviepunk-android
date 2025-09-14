package com.codepunk.moviepunk.data.remote.converter

import kotlinx.serialization.SerialName
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EnumConverterFactory @Inject constructor(): Converter.Factory() {

    override fun stringConverter(
        type: Type,
        annotations: Array<out Annotation?>,
        retrofit: Retrofit
    ): Converter<*, String>? = if (type is Class<*> && type.isEnum) {
        Converter<Enum<*>, String> { enum ->
            try {
                val field = enum.javaClass.getField(enum.name)
                val serialName = field.getAnnotation(SerialName::class.java)
                serialName?.value
            } catch (_: Exception) {
                null
            }
        }
    } else {
        null
    }

}
