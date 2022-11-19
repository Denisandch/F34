package com.example.data.network.model.productdetails

import com.squareup.moshi.Json

data class InfoAboutCheckedDeviceJsonAnswer(
    @Json(name = "CPU") val cpu: String,
    val camera: String,
    @Json(name = "capacity") val romCapacity: List<String>,
    @Json(name = "color") val colors: List<String>,
    val id: String,
    val images: List<String>,
    val isFavorites: Boolean = false,
    val price: Int,
    val rating: Float,
    @Json(name = "sd") val maxSdCapacity: String,
    @Json(name = "ssd") val ramCapacity: String,
    val title: String
)
