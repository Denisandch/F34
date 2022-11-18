package com.example.domain.repository

import com.example.domain.model.cart.CartInfo
import com.example.domain.model.mainscreen.MainScreenData
import com.example.domain.model.productdatails.CheckedDeviceInfo

interface DeviceRepositoryInterface {
   suspend fun getDevices(typeOfDevices: String): MainScreenData
   suspend fun getCartInfo(savedCart: String): CartInfo
   suspend fun getDetailInfo(checkedDevice: String): CheckedDeviceInfo
}