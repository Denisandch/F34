package com.example.domain.usecases

import com.example.domain.model.cart.CartInfo
import com.example.domain.repository.DeviceRepositoryInterface

class GetSavedCartInfo(private val devicesRepository: DeviceRepositoryInterface) {

    suspend fun getCartInfo(savedCart: String): CartInfo {
        return devicesRepository.getCartInfo(savedCart)
    }

}