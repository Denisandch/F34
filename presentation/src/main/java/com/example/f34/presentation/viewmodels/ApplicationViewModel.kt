package com.example.f34.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.mainscreen.BestSellerDevice
import com.example.domain.model.mainscreen.HotSellDevice
import com.example.domain.model.cart.CartInfo
import com.example.domain.model.cart.Lot
import com.example.domain.model.mainscreen.MainScreenData
import com.example.domain.model.productdatails.CheckedDeviceInfo
import com.example.domain.usecases.GetDetailInfo
import com.example.domain.usecases.GetDevicesMainScreenUseCase
import com.example.domain.usecases.GetSavedCartInfo
import com.example.f34.presentation.Constans
import kotlinx.coroutines.launch

class ApplicationViewModel(
    private val getCheckedDeviceInfo: GetDetailInfo,
    private val getDevicesMainScreenUseCase: GetDevicesMainScreenUseCase,
    private val getSavedCartInfo: GetSavedCartInfo

) : ViewModel() {

    private val _bestSellerList = MutableLiveData<List<BestSellerDevice>>()
    val bestSellerList: LiveData<List<BestSellerDevice>> = _bestSellerList

    private val _hotSalesList = MutableLiveData<List<HotSellDevice>>()
    val hotSalesList: LiveData<List<HotSellDevice>> = _hotSalesList

    private val _checkedDeviceInfo = MutableLiveData<CheckedDeviceInfo>()
    val checkedDeviceInfo: LiveData<CheckedDeviceInfo> = _checkedDeviceInfo

    private val _connectionResult = MutableLiveData<String>()
    val connectionResult: LiveData<String> = _connectionResult

    private val _cartInfo = MutableLiveData<CartInfo>()
    val cartInfo: LiveData<CartInfo> = _cartInfo

    private val _totalPrice = MutableLiveData<Int>()
    val totalPrice: LiveData<Int> = _totalPrice

    private val _totalCountDevices = MutableLiveData<Int>(0)
    val totalCountDevices: LiveData<Int> = _totalCountDevices

    private val _listOfBrands = MutableLiveData(emptySet<String>().toMutableSet())
    val listOfBrands: LiveData<MutableSet<String>> = _listOfBrands

    private lateinit var mainScreenData: MainScreenData
    private var filteredList = mutableListOf<BestSellerDevice>()
    private var mainDataIsDownloaded = false
    private var detailsDataIsDownloaded = false

    fun addLotToCart(lot: Lot) {
        if (_cartInfo.value!!.basket.contains(lot)) {
            upCountOfLot(lot)
        } else {
            _totalCountDevices.value = _totalCountDevices.value?.plus(1)
            _cartInfo.value?.basket?.add(lot)
            _cartInfo.postValue(_cartInfo.value)
            _totalPrice.value = _totalPrice.value?.plus(lot.totalPrice)
        }
    }

    fun upCountOfLot(lot: Lot) {
        val index = _cartInfo.value!!.basket.indexOf(lot)
        if (_cartInfo.value!!.basket[index].count == Constans.MAX_COUNT_OF_LOT_DEVICES_IN_CART) return

        _totalCountDevices.value = _totalCountDevices.value?.plus(1)
        _cartInfo.value!!.basket[index].count++
        _cartInfo.value!!.basket[index].totalPrice += lot.price
        _cartInfo.postValue(_cartInfo.value)
        _totalPrice.value = _totalPrice.value?.plus(_cartInfo.value!!.basket[index].price)
    }

    fun downCountOfLot(lot: Lot) {
        if (lot.count == 1) {
            deleteLotFromCart(lot)
            return
        }
        val index = _cartInfo.value!!.basket.indexOf(lot)
        _totalCountDevices.value = _totalCountDevices.value?.minus(1)
        _cartInfo.value!!.basket[index].count--
        cartInfo.value!!.basket[index].totalPrice -= lot.price
        _cartInfo.postValue(_cartInfo.value)
        _totalPrice.value = _totalPrice.value?.minus(_cartInfo.value!!.basket[index].price)
    }

    fun deleteLotFromCart(lot: Lot) {
        val index = _cartInfo.value!!.basket.indexOf(lot)
        _totalCountDevices.value =
            _totalCountDevices.value?.minus(_cartInfo.value!!.basket[index].count)
        _totalPrice.value = _totalPrice.value?.minus(_cartInfo.value!!.basket[index].totalPrice)
        _cartInfo.value!!.basket.remove(lot)
        _cartInfo.postValue(_cartInfo.value)
    }

    private fun initialProperties() {

        for (item in _cartInfo.value!!.basket) {
            _totalCountDevices.value = _totalCountDevices.value?.plus(item.count)
        }

        _totalPrice.value = _cartInfo.value!!.total
        _bestSellerList.value = mainScreenData.bestSellerList
        _hotSalesList.value = mainScreenData.hotSellList

        for (item in _bestSellerList.value!!) {
            _listOfBrands.value!!.add(item.title.substringBefore(" "))
        }
    }

    fun downloadInitialData() {
        if (!mainDataIsDownloaded) {
            viewModelScope.launch {
                try {
                    mainScreenData =
                        getDevicesMainScreenUseCase.getDevicesForMainScreen("654bd15e-b121-49ba-a588-960956b15175")

                    _cartInfo.value =
                        getSavedCartInfo.getCartInfo("53539a72-3c5f-4f30-bbb1-6ca10d42c149")

                    initialProperties()
                    _connectionResult.value = Constans.SUCCESS
                    mainDataIsDownloaded = true

                } catch (e: Exception) {
                    _connectionResult.value = Constans.ERROR
                }
            }
        }
    }

    fun downloadInfoCheckedDevice(checkedDevice: String = "6c14c560-15c6-4248-b9d2-b4508df7d4f5") {
        if (!detailsDataIsDownloaded) {
            viewModelScope.launch {
                try {
                    _checkedDeviceInfo.value = getCheckedDeviceInfo.getDetailInfo(checkedDevice)
                    _connectionResult.value = Constans.SUCCESS
                    detailsDataIsDownloaded = true
                } catch (e: Exception) {
                    _connectionResult.value = Constans.ERROR
                }
            }
        }
    }

    fun invertFavorite(device: BestSellerDevice) {
        val index = _bestSellerList.value!!.indexOf(device)
        _bestSellerList.value!![index].isFavorites = !_bestSellerList.value!![index].isFavorites
    }

    fun applyFilters(brand: String = "", lowPrice: Int = 0, highPrice: Int = 10000) {
        for (device in mainScreenData.bestSellerList) {
            if (device.title.contains(brand)
                && device.discountPrice >= lowPrice
                && device.discountPrice <= highPrice
            )
                filteredList.add(device)
        }
        _bestSellerList.postValue(filteredList)
        filteredList = mutableListOf()
    }
}