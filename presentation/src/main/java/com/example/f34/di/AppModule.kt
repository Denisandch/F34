package com.example.f34.di

import com.example.f34.presentation.viewmodels.ApplicationViewModel
import org.koin.dsl.module

val appModule = module {

    single<ApplicationViewModel> {
        ApplicationViewModel(
            getCheckedDeviceInfo = get(),
            getSavedCartInfo = get(),
            getDevicesMainScreenUseCase = get()
        )
    }
}