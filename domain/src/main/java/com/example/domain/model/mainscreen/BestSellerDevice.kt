package com.example.domain.model.mainscreen


data class BestSellerDevice(
    val id: Int,
    var isFavorites: Boolean,
    val title: String,
    val discountPrice: Int,
    val priceWithoutDiscount: Int,
    val picture: String
)
