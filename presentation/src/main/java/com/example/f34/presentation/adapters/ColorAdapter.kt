package com.example.f34.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.f34.R
import com.example.f34.databinding.OneItemColorBinding


class ColorAdapter: RecyclerView.Adapter<ColorAdapter.ColorItemViewHolder>() {
    private var listOfColor = emptyList<String>()

    class ColorItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val oneColorBinding = OneItemColorBinding.bind(view)

        fun bind(item: String) {
            oneColorBinding.color.background.colorFilter =
                BlendModeColorFilterCompat
                    .createBlendModeColorFilterCompat(item.toColorInt(), BlendModeCompat.SRC_ATOP)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorItemViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.one_item_color,parent,false)
        return ColorItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorItemViewHolder, position: Int) {
        holder.bind(listOfColor[position])
    }

    override fun getItemCount(): Int {
        return listOfColor.size
    }

    fun setList(listOfColor: List<String>) {
        this.listOfColor = listOfColor
        notifyDataSetChanged()
    }

}