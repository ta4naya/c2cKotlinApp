package com.example.c2capp
import kotlinx.serialization.Serializable

@Serializable
data class Carousel(
    val carouselId: Int,
    var content: String? = null,
    var picsPath: String? = null
) {
    val id: Int
        get() = carouselId
}
