package com.example.domain.model.productdatails

data class CheckedDeviceInfo(
    val cpu: String,
    val camera: String,
    val romCapacity: List<String>,
    val colors: List<String>,
    val id: String,
    val images: List<String>,
    val isFavorites: Boolean = false,
    val price: Int,
    val rating: Float,
    val maxSdCapacity: String,
    val ramCapacity: String,
    val title: String
)
