package com.example.f34.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.network.downloadapi.DownloadApi
import com.example.domain.model.mainscreen.BestSellerDevice
import com.example.domain.model.mainscreen.HotSellDevice
import com.example.data.repository.DeviceRepositoryImplementation
import com.example.domain.model.cart.CartInfo
import com.example.domain.model.cart.Lot
import com.example.domain.model.mainscreen.MainScreenData
import com.example.domain.model.productdatails.CheckedDeviceInfo
import com.example.domain.usecases.GetDetailInfo
import com.example.domain.usecases.GetDevicesMainScreenUseCase
import com.example.domain.usecases.GetSavedCartInfo
import kotlinx.coroutines.launch

class ApplicationViewModel : ViewModel() {

    private val deviceRepositoryImplementation = DeviceRepositoryImplementation(DownloadApi())

    private val getCheckedDeviceInfo = GetDetailInfo(deviceRepositoryImplementation)
    private val getDevicesMainScreenUseCase = GetDevicesMainScreenUseCase(deviceRepositoryImplementation)
    private val getSavedCartInfo = GetSavedCartInfo(deviceRepositoryImplementation)


    private val _bestSellerList = MutableLiveData<List<BestSellerDevice>>()
    val bestSellerList: LiveData<List<BestSellerDevice>> = _bestSellerList

    private val _hotSalesList = MutableLiveData<List<HotSellDevice>>()
    val hotSalesList: LiveData<List<HotSellDevice>> = _hotSalesList

    private val _checkedDeviceInfo = MutableLiveData<CheckedDeviceInfo>()
    val checkedDeviceInfo: LiveData<CheckedDeviceInfo> = _checkedDeviceInfo

    private val _cartInfo = MutableLiveData<CartInfo>()
    val cartInfo: LiveData<CartInfo> = _cartInfo

    private val _totalPrice = MutableLiveData<Int>()
    val totalPrice: LiveData<Int> = _totalPrice

    private lateinit var mainScreenData: MainScreenData
    private var dataIsDownloaded = false

    fun addLotToCart(lot: Lot) {
        if(_cartInfo.value!!.basket.contains(lot)) {
            upCountOfLot(lot)
        } else {
            _cartInfo.value?.basket?.add(lot)
            _cartInfo.postValue(_cartInfo.value)
            _totalPrice.value = _totalPrice.value?.plus(lot.totalPrice)
        }
    }

    fun upCountOfLot(lot: Lot) {
        if(lot.count == 9) return
        val index =_cartInfo.value!!.basket.indexOf(lot)
        _cartInfo.value!!.basket[index].count++
        _cartInfo.value!!.basket[index].totalPrice += lot.price
        _cartInfo.postValue(_cartInfo.value)
        _totalPrice.value = _totalPrice.value?.plus(_cartInfo.value!!.basket[index].price)
    }

    fun downCountOfLot(lot: Lot) {
        if(lot.count == 1) {
            deleteLotFromCart(lot)
            return
        }
        val index =_cartInfo.value!!.basket.indexOf(lot)
        _cartInfo.value!!.basket[index].count--
        cartInfo.value!!.basket[index].totalPrice -= lot.price
        _cartInfo.postValue(_cartInfo.value)
        _totalPrice.value = _totalPrice.value?.minus(_cartInfo.value!!.basket[index].price)
    }

    fun deleteLotFromCart(lot: Lot) {
        val index =_cartInfo.value!!.basket.indexOf(lot)
        _totalPrice.value = _totalPrice.value?.minus(_cartInfo.value!!.basket[index].totalPrice)
        _cartInfo.value!!.basket.remove(lot)
        _cartInfo.postValue(_cartInfo.value)
    }

    fun downloadInitialData() {
        if(!dataIsDownloaded) {
            viewModelScope.launch {
                mainScreenData =
                    getDevicesMainScreenUseCase.getDevicesForMainScreen("654bd15e-b121-49ba-a588-960956b15175")

                _cartInfo.value =
                    getSavedCartInfo.getCartInfo("53539a72-3c5f-4f30-bbb1-6ca10d42c149")

                _totalPrice.value = _cartInfo.value!!.total

                _bestSellerList.value = mainScreenData.bestSellerList
                _hotSalesList.value = mainScreenData.hotSellList
            }
            dataIsDownloaded = true
        }
    }

    fun downloadInfoCheckedDevice(checkedDevice: String = "6c14c560-15c6-4248-b9d2-b4508df7d4f5") {
        viewModelScope.launch {
            _checkedDeviceInfo.value = getCheckedDeviceInfo.getDetailInfo(checkedDevice)
        }
    }

    fun invertFavorite(device: BestSellerDevice) {
        val index =_bestSellerList.value!!.indexOf(device)
        _bestSellerList.value!![index].isFavorites = !_bestSellerList.value!![index].isFavorites
    }
}