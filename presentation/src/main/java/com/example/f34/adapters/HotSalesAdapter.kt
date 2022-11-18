package com.example.f34.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.domain.model.mainscreen.HotSellDevice
import com.example.f34.R
import com.example.f34.databinding.OneItemHotSalesBinding


class HotSalesAdapter: ListAdapter<HotSellDevice, HotSalesAdapter.HotSalesItemViewHolder>(DiffCallback) {

    class HotSalesItemViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val oneItemHotSalesBinding = OneItemHotSalesBinding.bind(view)

        fun init(device: HotSellDevice) {
            if(device.isNew) oneItemHotSalesBinding.isNewMarker.visibility = View.VISIBLE
            oneItemHotSalesBinding.hotSaleImage.load(device.picture.toUri().buildUpon().build()){
                placeholder(R.drawable.loading_animation)
            }
            oneItemHotSalesBinding.hotSaleTitle.text = device.title
            oneItemHotSalesBinding.hotSaleDescription.text = device.subtitle

            itemView.setOnClickListener {
                itemView.findNavController().navigate(R.id.action_explorer_to_productDetailsFragment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotSalesItemViewHolder {
        return HotSalesItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.one_item_hot_sales,parent,false))
    }

    override fun onBindViewHolder(holder: HotSalesItemViewHolder, position: Int) {

        val device = getItem(position)
        holder.init(device)
    }

    object DiffCallback: DiffUtil.ItemCallback<HotSellDevice>() {

        override fun areItemsTheSame(oldItem: HotSellDevice, newItem: HotSellDevice): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: HotSellDevice, newItem: HotSellDevice): Boolean {
            return oldItem.picture == newItem.picture
        }

    }

}