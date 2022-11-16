package com.example.data.network.model.mainscreen

import com.squareup.moshi.Json

data class HotSellDevice(
    val id: Int,
    @Json(name = "is_new") val isNew: Boolean = false,
    val title: String,
    val subtitle: String,
    val picture: String,
    @Json(name = "is_buy") val isBuy: Boolean
)
