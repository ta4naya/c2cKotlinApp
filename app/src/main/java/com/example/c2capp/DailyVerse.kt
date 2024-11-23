package com.example.c2capp

import kotlinx.serialization.Serializable

@Serializable
data class DailyVerse(
    val dailyVerseId: Int,
    val verse: String? = null,
    val picsPath: String? = null
) {
    val id: Int
        get() = dailyVerseId
}
