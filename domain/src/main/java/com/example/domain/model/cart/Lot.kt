package com.example.domain.model.cart

data class Lot(
    val id: Int,
    val images: String,
    var count: Int = 1,
    val price: Int,
    var totalPrice: Int = price * count,
    val title: String
) {
    override fun equals(other: Any?): Boolean {
        return (other is Lot) && other.id == id
    }
}
