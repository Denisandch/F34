package com.example.domain.model.cart

data class CartInfo(
    var basket: MutableList<Lot>,
    val deliveryString: String = "",
    val id: String,
    var total: Int
)
