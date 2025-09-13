package com.codepunk.moviepunk.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TimeWindow(val value: String) {
    @SerialName("day")
    DAY("day"),

    @SerialName("week")
    WEEK("week")
}
