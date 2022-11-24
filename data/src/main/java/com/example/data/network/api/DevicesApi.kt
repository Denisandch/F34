package com.example.data.network.api

import com.example.data.network.model.cart.CartInfoJsonAnswer
import com.example.data.network.model.mainscreen.MainScreenJsonAnswer
import com.example.data.network.model.productdetails.InfoAboutCheckedDeviceJsonAnswer
import retrofit2.http.GET
import retrofit2.http.Path

interface DevicesApi {

    @GET("v3/{listOfDevicesMainScreen}")
    suspend fun getDevicesFromMainScreen(
        @Path("listOfDevicesMainScreen") listOfDevicesMainScreen: String
    ): MainScreenJsonAnswer

    @GET("v3/{checkedDevice}")
    suspend fun getDetailsAboutDevices(
        @Path("checkedDevice") checkedDevice: String
    ): InfoAboutCheckedDeviceJsonAnswer

    @GET("v3/{savedCart}")
    suspend fun getCartInfo(
        @Path("savedCart") savedCart: String
    ): CartInfoJsonAnswer

}