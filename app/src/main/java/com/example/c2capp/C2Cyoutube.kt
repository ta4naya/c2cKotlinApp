package com.example.c2capp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class C2Cyoutube(
    @SerialName("c2CyoutubeId") val id: Int,
    @SerialName("hosts") var hosts: String? = null,
    @SerialName("title") var title: String? = null,
    @SerialName("picsPath") var picsPath: String? = null,
    @SerialName("videoURL") var videoURL: String? = null,
    @SerialName("day") var day: Int
)
