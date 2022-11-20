package com.example.f34.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.domain.model.cart.Lot

import com.example.f34.R
import com.example.f34.databinding.OneItemCartBinding

class CartAdapter(val listener: CartInterface): ListAdapter<Lot, CartAdapter.CartItemViewHolder>(
    DiffCallback
) {

    inner class CartItemViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val oneItemCartBinding = OneItemCartBinding.bind(view)

        fun init(lot: Lot) {
            oneItemCartBinding.apply {
                cartImage.load(lot.images.toUri().buildUpon().build()){
                    placeholder(R.drawable.loading_animation)
                }
                cartTitle.text = lot.title
                cartPrice.text = ("$${lot.totalPrice}")
                cartItemCounter.text = lot.count.toString()

                cartCountPlus.setOnClickListener{
                    listener.upCount(lot)
                }

                cartCountMinus.setOnClickListener{
                    listener.downCount(lot)
                }

                deleteButton.setOnClickListener {
                    listener.deleteFromCart(lot)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        return CartItemViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.one_item_cart,parent,false))
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {

        val item = getItem(position)
        holder.init(item)
    }

    object DiffCallback: DiffUtil.ItemCallback<Lot>() {

        override fun areItemsTheSame(oldItem: Lot, newItem: Lot): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Lot, newItem: Lot): Boolean {
            return oldItem.title == newItem.title
        }

    }

}

interface CartInterface {
    fun upCount(lot: Lot)
    fun downCount(lot: Lot)
    fun deleteFromCart(lot: Lot)
}