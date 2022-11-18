package com.example.domain.usecases

import com.example.domain.model.productdatails.CheckedDeviceInfo
import com.example.domain.repository.DeviceRepositoryInterface

class GetDetailInfo(private val devicesRepository: DeviceRepositoryInterface) {

    suspend fun getDetailInfo(checkedDevice: String): CheckedDeviceInfo {
        return devicesRepository.getDetailInfo(checkedDevice)
    }
}