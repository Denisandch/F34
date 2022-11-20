package com.example.f34.di

import androidx.recyclerview.widget.ListAdapter
import com.example.f34.presentation.adapters.BestSellerAdapter
import com.example.f34.presentation.viewmodels.ApplicationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.dsl.single

val appModule = module {

    single<ApplicationViewModel> {
        ApplicationViewModel(
            getCheckedDeviceInfo = get(),
            getSavedCartInfo = get(),
            getDevicesMainScreenUseCase = get()
        )
    }
}