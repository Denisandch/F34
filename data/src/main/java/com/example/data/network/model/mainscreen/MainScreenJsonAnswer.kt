package com.example.data.network.model.mainscreen

import com.squareup.moshi.Json

data class MainScreenJsonAnswer(
    @Json(name = "home_store") val hotSellList: List<HotSellDevice>,
    @Json(name = "best_seller") val bestSellerList: List<BestSellerDevice>
)
