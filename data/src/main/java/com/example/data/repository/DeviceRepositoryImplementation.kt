package com.example.data.repository

import com.example.data.network.downloadapi.DeviceNetworkDownload
import com.example.data.network.model.cart.CartInfoJsonAnswer
import com.example.data.network.model.mainscreen.MainScreenJsonAnswer
import com.example.data.network.model.productdetails.InfoAboutCheckedDeviceJsonAnswer
import com.example.domain.model.cart.CartInfo
import com.example.domain.model.cart.Lot
import com.example.domain.model.mainscreen.BestSellerDevice
import com.example.domain.model.mainscreen.HotSellDevice
import com.example.domain.model.mainscreen.MainScreenData
import com.example.domain.model.productdatails.CheckedDeviceInfo
import com.example.domain.repository.DeviceRepositoryInterface

class DeviceRepositoryImplementation(private val deviceNetworkDownload: DeviceNetworkDownload): DeviceRepositoryInterface {

    override suspend fun getDevices(typeOfDevices: String): MainScreenData {
        val temporary = deviceNetworkDownload.downloadMainScreen(typeOfDevices)

        return mapJsonToDataMainScreen(temporary)
    }

    override suspend fun getCartInfo(savedCart: String): CartInfo {
        val temporary = deviceNetworkDownload.downloadCartInfo(savedCart)

        return mapJsonToInfoCart(temporary)
    }

    override suspend fun getDetailInfo(checkedDevice: String): CheckedDeviceInfo {
        val temporary = deviceNetworkDownload.downloadCheckedDeviceInfo(checkedDevice)

        return mapJsonToInfoCheckedDevice(temporary)
    }

    private fun mapJsonToDataMainScreen(temporary: MainScreenJsonAnswer): MainScreenData{

        val newHot = mutableListOf<HotSellDevice>()
        val newBest = mutableListOf<BestSellerDevice>()

        for(item in temporary.hotSellList) {
            newHot.add(
                HotSellDevice(
                    item.id,
                    item.isNew,
                    item.title,
                    item.subtitle,
                    item.picture,
                    item.isBuy
                )
            )
        }
        for(item in temporary.bestSellerList) {
            newBest.add(
                BestSellerDevice(
                    item.id,
                    item.isFavorites,
                    item.title,
                    item.discountPrice,
                    item.priceWithoutDiscount,
                    item.picture
                )
            )
        }
        return MainScreenData(newHot, newBest)
    }
    private fun mapJsonToInfoCart(temporary: CartInfoJsonAnswer): CartInfo {
        val newBasket = mutableListOf<Lot>()

        for (item in temporary.basket) {
            newBasket.add(
                Lot(
                    id = item.id,
                    title = item.title,
                    price = item.price,
                    images = item.images
                )
            )
        }

        return CartInfo(
            basket = newBasket,
            deliveryString = temporary.deliveryString,
            id = temporary.id,
            total = temporary.total
        )
    }
    private fun mapJsonToInfoCheckedDevice(temporary: InfoAboutCheckedDeviceJsonAnswer): CheckedDeviceInfo {
        val newInfo = CheckedDeviceInfo(
            cpu = temporary.cpu,
            camera = temporary.camera,
            romCapacity = temporary.romCapacity,
            colors = temporary.colors,
            id = temporary.id,
            images = temporary.images,
            isFavorites = temporary.isFavorites,
            price = temporary.price,
            rating = temporary.rating,
            maxSdCapacity = temporary.maxSdCapacity,
            ramCapacity = temporary.ramCapacity,
            title = temporary.title
        )

        return newInfo
    }
}