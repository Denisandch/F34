package com.example.data.network.downloadapi

import com.example.data.network.api.RetrofitInstance
import com.example.data.network.model.cart.CartInfoJsonAnswer
import com.example.data.network.model.mainscreen.MainScreenJsonAnswer
import com.example.data.network.model.productdetails.InfoAboutCheckedDeviceJsonAnswer

class DownloadApi : DeviceNetworkDownload {
    override suspend fun downloadMainScreen(typeOfDevices: String): MainScreenJsonAnswer {
        return RetrofitInstance.retrofitService.getDevicesFromMainScreen(typeOfDevices)
    }

    override suspend fun downloadCartInfo(savedCart: String): CartInfoJsonAnswer {
        return RetrofitInstance.retrofitService.getCartInfo(savedCart)
    }

    override suspend fun downloadCheckedDeviceInfo(checkedDevice: String): InfoAboutCheckedDeviceJsonAnswer {
        return RetrofitInstance.retrofitService.getDetailsAboutDevices(checkedDevice)
    }
}