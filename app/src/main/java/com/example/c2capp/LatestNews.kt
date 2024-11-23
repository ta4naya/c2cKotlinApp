package com.example.c2capp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LatestNews(
    @SerialName("postId") val id: Int,
    @SerialName("content") var content: String? = null,
    @SerialName("title") var title: String? = null,
    @SerialName("picsPath") var picsPath: String? = null
)

