package com.codepunk.moviepunk.data.mapper

import com.codepunk.moviepunk.data.local.entity.ConfigurationItemEntity
import com.codepunk.moviepunk.data.local.entity.ConfigurationKey
import com.codepunk.moviepunk.data.remote.dto.ConfigurationDto
import com.codepunk.moviepunk.domain.model.Configuration
import com.codepunk.moviepunk.domain.model.Configuration.ConfigurationImages

// ====================
// Dto to entities
// ====================

fun ConfigurationDto.toEntities(): List<ConfigurationItemEntity> = buildList {
    addAll(
        this@toEntities.changeKeys.map { value ->
            ConfigurationItemEntity(
                key = ConfigurationKey.CHANGE_KEYS,
                value = value
            )
        }
    )
    add(
        ConfigurationItemEntity(
            key = ConfigurationKey.IMAGES_BASE_URL,
            value = this@toEntities.images.baseUrl
        )
    )
    add(
        ConfigurationItemEntity(
            key = ConfigurationKey.IMAGES_SECURE_BASE_URL,
            value = this@toEntities.images.secureBaseUrl
        )
    )
    addAll(
        this@toEntities.images.backdropSizes.map { value ->
            ConfigurationItemEntity(
                key = ConfigurationKey.IMAGES_BACKDROP_SIZES,
                value = value
            )
        }
    )
    addAll(
        this@toEntities.images.logoSizes.map { value ->
            ConfigurationItemEntity(
                key = ConfigurationKey.IMAGES_LOGO_SIZES,
                value = value
            )
        }
    )
    addAll(
        this@toEntities.images.posterSizes.map { value ->
            ConfigurationItemEntity(
                key = ConfigurationKey.IMAGES_POSTER_SIZES,
                value = value
            )
        }
    )
    addAll(
        this@toEntities.images.profileSizes.map { value ->
            ConfigurationItemEntity(
                key = ConfigurationKey.IMAGES_PROFILE_SIZES,
                value = value
            )
        }
    )
    addAll(
        this@toEntities.images.stillSizes.map { value ->
            ConfigurationItemEntity(
                key = ConfigurationKey.IMAGES_STILL_SIZES,
                value = value
            )
        }
    )
}

// ====================
// Entities to domain
// ====================

fun List<ConfigurationItemEntity>.toModel(): Configuration {
    val map = this.groupBy { entity ->
        entity.key
    }.mapValues { (_, value) ->
        value.sortedBy {
            entity -> entity.id
        }.map { entity ->
            entity.value
        }
    }
    return Configuration(
        changeKeys = map[ConfigurationKey.CHANGE_KEYS].orEmpty(),
        images = ConfigurationImages(
            baseUrl = map[ConfigurationKey.IMAGES_BASE_URL]?.firstOrNull() ?: "",
            secureBaseUrl = map[ConfigurationKey.IMAGES_SECURE_BASE_URL]?.firstOrNull() ?: "",
            backdropSizes = map[ConfigurationKey.IMAGES_BACKDROP_SIZES].orEmpty(),
            logoSizes = map[ConfigurationKey.IMAGES_LOGO_SIZES].orEmpty(),
            posterSizes = map[ConfigurationKey.IMAGES_POSTER_SIZES].orEmpty(),
            profileSizes = map[ConfigurationKey.IMAGES_PROFILE_SIZES].orEmpty(),
            stillSizes = map[ConfigurationKey.IMAGES_STILL_SIZES].orEmpty()
        )
    )
}
