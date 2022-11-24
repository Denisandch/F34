package com.example.f34.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.f34.R


class ViewPagerImageAdapter : RecyclerView.Adapter<ViewPagerImageAdapter.ViewPagerHolder>() {
    private var listOfImage = emptyList<String>()

    class ViewPagerHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = itemView.findViewById<ImageView>(R.id.image_view_view_pager)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.one_item_image, parent, false)
        return ViewPagerHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        holder.imageView.load(listOfImage[position].toUri().buildUpon().build())
    }

    override fun getItemCount(): Int {
        return listOfImage.size
    }

    fun setList(listOfImage: List<String>) {
        this.listOfImage = listOfImage
        notifyDataSetChanged()
    }

}