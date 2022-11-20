package com.example.f34.di

import com.example.data.network.downloadapi.DeviceNetworkDownload
import com.example.data.network.downloadapi.DownloadApi
import com.example.data.repository.DeviceRepositoryImplementation
import com.example.domain.repository.DeviceRepositoryInterface
import org.koin.dsl.module
import kotlin.math.sin

val dataModule = module {

    single<DeviceNetworkDownload> {
        DownloadApi()
    }

    single<DeviceRepositoryInterface>{
        DeviceRepositoryImplementation(deviceNetworkDownload = get())
    }

}