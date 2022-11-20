package com.example.f34

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.domain.model.mainscreen.BestSellerDevice
import com.example.f34.adapters.BestSellerAdapter
import com.example.f34.adapters.BestSellerInterface
import com.example.f34.adapters.HotSalesAdapter
import com.example.f34.databinding.FragmentMainScreenBinding
import com.example.f34.viewmodels.ApplicationViewModel
import com.google.android.material.tabs.TabLayout


class MainScreenFragment : Fragment(), BestSellerInterface {

    private val imageArray = listOf(
        R.drawable.category_phones,
        R.drawable.category_computer,
        R.drawable.category_health,
        R.drawable.category_books,
        R.drawable.category_books
    )
    private val adapterBest = BestSellerAdapter(this)
    private val adapterHot = HotSalesAdapter()
    private val viewmodel: ApplicationViewModel by activityViewModels()
    private lateinit var fragmentMainScreenBinding: FragmentMainScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMainScreenBinding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return fragmentMainScreenBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentMainScreenBinding.toolBar.mainScreenToolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.filter -> findNavController().navigate(R.id.action_explorer_to_bottomSheetFragment)
            }
            true
        }

            for (i in 0 until fragmentMainScreenBinding.categoriesTabLayout.tabCount) {
                val shape = LayoutInflater.from(requireContext()).inflate(R.layout.one_item_categories, null) as LinearLayout
                val textView = shape.findViewById<TextView>(R.id.text_type_of_device)
                val imageView = shape.findViewById<ImageView>(R.id.category_image)

                textView.text = fragmentMainScreenBinding.categoriesTabLayout.getTabAt(i)!!.text.toString()
                imageView.setImageResource(imageArray[i])
                fragmentMainScreenBinding.categoriesTabLayout.getTabAt(i)!!.customView = shape
            }


        fragmentMainScreenBinding.categoriesTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                highlightTab(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                fadeTab(tab.position)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                highlightTab(tab.position)
            }

        })

        fragmentMainScreenBinding.hotSalesRecycler.adapter = adapterHot
        fragmentMainScreenBinding.bestSellerRecycler.adapter = adapterBest

        viewmodel.hotSalesList.observe(viewLifecycleOwner) {
            adapterHot.submitList(it)
        }
        viewmodel.bestSellerList.observe(viewLifecycleOwner) {
            adapterBest.submitList(it)
        }

        viewmodel.downloadInitialData()
    }

    override fun tapFavorites(device: BestSellerDevice) {
        viewmodel.invertFavorite(device)
    }

    fun highlightTab(position: Int) {
        val tabShape = fragmentMainScreenBinding.categoriesTabLayout.getTabAt(position)!!.customView as LinearLayout
        val background: FrameLayout = tabShape.findViewById(R.id.category_background)
        val textView = tabShape.findViewById<TextView>(R.id.text_type_of_device)

        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.light_orange))
        background.setBackgroundResource(R.drawable.shape_oval_orange)
    }

    fun fadeTab(position: Int) {
        val tabShape = fragmentMainScreenBinding.categoriesTabLayout.getTabAt(position)!!.customView as LinearLayout
        val background: FrameLayout = tabShape.findViewById(R.id.category_background)
        val textView = tabShape.findViewById<TextView>(R.id.text_type_of_device)

        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
        background.setBackgroundResource(R.drawable.shape_oval_white)
    }
}