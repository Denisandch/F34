package com.example.f34.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.network.api.RetrofitInstance
import com.example.data.network.model.cart.CartInfoJsonAnswer
import com.example.data.network.model.mainscreen.BestSellerDevice
import com.example.data.network.model.mainscreen.HotSellDevice
import com.example.data.network.model.mainscreen.MainScreenJsonAnswer
import kotlinx.coroutines.launch

class MainScreenViewModel : ViewModel() {
    val BEST = MutableLiveData<List<BestSellerDevice>>()
    val HOT = MutableLiveData<List<HotSellDevice>>()
    val error = MutableLiveData<String>()
    val cartAnswer = MutableLiveData<CartInfoJsonAnswer>()
    val retrofin = RetrofitInstance
    lateinit var answer: MainScreenJsonAnswer

    fun download() {
        viewModelScope.launch {
            answer =
                retrofin.retrofitService.getDevicesFromMainScreen("654bd15e-b121-49ba-a588-960956b15175")
            cartAnswer.value =
                retrofin.retrofitService.getCartInfo("53539a72-3c5f-4f30-bbb1-6ca10d42c149")
            BEST.value = answer.bestSellerList
            HOT.value = answer.hotSellList
        }
    }

}