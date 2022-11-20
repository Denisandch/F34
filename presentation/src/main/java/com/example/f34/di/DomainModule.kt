package com.example.f34.di

import com.example.domain.usecases.GetDetailInfo
import com.example.domain.usecases.GetDevicesMainScreenUseCase
import com.example.domain.usecases.GetSavedCartInfo
import org.koin.dsl.module

val domainModule = module {

    factory<GetDetailInfo>{
        GetDetailInfo(devicesRepository = get())
    }

    factory<GetDevicesMainScreenUseCase>{
        GetDevicesMainScreenUseCase(devicesRepository = get())
    }

    factory<GetSavedCartInfo>{
        GetSavedCartInfo(devicesRepository = get())
    }

}