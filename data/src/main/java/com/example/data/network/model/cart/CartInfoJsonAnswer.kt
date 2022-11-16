package com.example.data.network.model.cart

import com.squareup.moshi.Json

data class CartInfoJsonAnswer(
    val basket: List<Lot>,
    @Json(name = "delivery") val deliveryString: String = "",
    val id: String,
    val total: Int
)
