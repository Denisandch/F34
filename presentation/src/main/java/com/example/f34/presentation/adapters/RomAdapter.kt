package com.example.f34.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.f34.R
import com.example.f34.databinding.OneItemRomBinding

class RomAdapter: RecyclerView.Adapter<RomAdapter.RomItemViewHolder>() {
    private var listOfCapacity = emptyList<String>()

    class RomItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val oneRomBinding = OneItemRomBinding.bind(view)

        fun bind(item: String) {
            oneRomBinding.capacity.text = "$item GB"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RomItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.one_item_rom,parent,false)
        return RomItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RomItemViewHolder, position: Int) {
        holder.bind(listOfCapacity[position])
    }

    override fun getItemCount(): Int {
        return listOfCapacity.size
    }

    fun setList(listOfCapacity: List<String>) {
        this.listOfCapacity = listOfCapacity
        notifyDataSetChanged()
    }

}