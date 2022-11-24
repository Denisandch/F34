package com.example.domain.usecases

import com.example.domain.model.mainscreen.MainScreenData
import com.example.domain.repository.DeviceRepositoryInterface

class GetDevicesMainScreenUseCase(private val devicesRepository: DeviceRepositoryInterface) {

    suspend fun getDevicesForMainScreen(typeOfDevices: String): MainScreenData {
        return devicesRepository.getDevices(typeOfDevices)
    }

}