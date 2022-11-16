package com.example.data.network.model.mainscreen

import com.squareup.moshi.Json

data class BestSellerDevice(
    val id: Int,
    @Json(name = "is_favorites") val isFavorites: Boolean,
    val title: String,
    @Json(name = "price_without_discount") val discountPrice: Int,
    @Json(name = "discount_price") val priceWithoutDiscount: Int,
    val picture: String
)
