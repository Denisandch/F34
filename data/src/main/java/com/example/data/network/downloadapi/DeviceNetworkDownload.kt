package com.example.data.network.downloadapi

import com.example.data.network.model.cart.CartInfoJsonAnswer
import com.example.data.network.model.mainscreen.MainScreenJsonAnswer
import com.example.data.network.model.productdetails.InfoAboutCheckedDeviceJsonAnswer

interface DeviceNetworkDownload {
    suspend fun downloadMainScreen(typeOfDevices: String): MainScreenJsonAnswer
    suspend fun downloadCartInfo(savedCart: String): CartInfoJsonAnswer
    suspend fun downloadCheckedDeviceInfo(checkedDevice: String): InfoAboutCheckedDeviceJsonAnswer
}