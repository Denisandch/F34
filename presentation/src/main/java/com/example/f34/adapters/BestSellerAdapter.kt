package com.example.f34.adapters

import android.text.Html
import android.text.Layout.Directions
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.data.network.model.mainscreen.BestSellerDevice
import com.example.f34.R
import com.example.f34.databinding.OneItemBestSellerBinding

class BestSellerAdapter: ListAdapter<BestSellerDevice, BestSellerAdapter.BestSellerItemViewHolder>(DiffCallback) {

    class BestSellerItemViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val oneItemBestSellerBinding = OneItemBestSellerBinding.bind(view)

        fun init(device: BestSellerDevice) {
//            if(device.isNew) oneItemBestSellerBinding.isNewMarker.visibility = View.VISIBLE
            oneItemBestSellerBinding.bestSellerImage.load(device.picture.toUri().buildUpon().build()){
                placeholder(R.drawable.loading_animation)
            }
            oneItemBestSellerBinding.price.text = ("$${device.discountPrice}")
            oneItemBestSellerBinding.priceWithoutDiscount.text = Html.fromHtml(
                "<s>"
                    + "$"
                    + device.priceWithoutDiscount.toString()
                    + "</s>")
            oneItemBestSellerBinding.title.text = device.title

            itemView.setOnClickListener {
                itemView.findNavController().navigate(R.id.action_explorer_to_productDetailsFragment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestSellerItemViewHolder {
        return BestSellerItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.one_item_best_seller,parent,false))
    }

    override fun onBindViewHolder(holder: BestSellerItemViewHolder, position: Int) {

        val device = getItem(position)
        holder.init(device)
    }

    object DiffCallback: DiffUtil.ItemCallback<BestSellerDevice>() {

        override fun areItemsTheSame(oldItem: BestSellerDevice, newItem: BestSellerDevice): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: BestSellerDevice, newItem: BestSellerDevice): Boolean {
            return oldItem.picture == newItem.picture
        }

    }

}