package com.dschonfeld.kmp.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("color") val color: String? = null,
    @SerialName("capacity") val capacity: String? = null
)