package com.example.f34.adapters

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.data.network.model.cart.Lot

import com.example.f34.R
import com.example.f34.databinding.OneItemCartBinding

class CartAdapter: ListAdapter<Lot, CartAdapter.CartItemViewHolder>(DiffCallback) {

    class CartItemViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val oneItemCartBinding = OneItemCartBinding.bind(view)

        fun init(item: Lot) {
            oneItemCartBinding.cartImage.load(item.images.toUri().buildUpon().build()){
                placeholder(R.drawable.loading_animation)
            }
            oneItemCartBinding.cartTitle.text = item.title
            oneItemCartBinding.cartPrice.text = ("$${item.price}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        return CartItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.one_item_cart,parent,false))
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {

        val item = getItem(position)
        holder.init(item)
    }

    object DiffCallback: DiffUtil.ItemCallback<Lot>() {

        override fun areItemsTheSame(oldItem: Lot, newItem: Lot): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Lot, newItem: Lot): Boolean {
            return oldItem.images == newItem.images
        }

    }

}