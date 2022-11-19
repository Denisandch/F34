package com.example.f34

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.domain.model.cart.Lot
import com.example.f34.adapters.ColorAdapter
import com.example.f34.adapters.RomAdapter
import com.example.f34.adapters.ViewPagerImageAdapter
import com.example.f34.databinding.FragmentProductDetailsBinding
import com.example.f34.viewmodels.ApplicationViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.math.abs


class ProductDetailsFragment : Fragment() {

    private val colorAdapter = ColorAdapter()
    private val romAdapter = RomAdapter()
    private val pagerAdapter = ViewPagerImageAdapter()

    private lateinit var fragmentProductDetailsBinding: FragmentProductDetailsBinding
    private val viewmodel: ApplicationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentProductDetailsBinding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return fragmentProductDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentProductDetailsBinding.toolBar.cart.setOnClickListener{
            val botNav = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
            findNavController().popBackStack(R.id.productDetailsFragment,true)
            botNav?.selectedItemId = R.id.cart
        }

        fragmentProductDetailsBinding.toolBar.exit.setOnClickListener{
            findNavController().popBackStack()
        }


        fragmentProductDetailsBinding.recyclerColor.adapter = colorAdapter
        fragmentProductDetailsBinding.recycletCapacity.adapter = romAdapter
        fragmentProductDetailsBinding.detailsImages.adapter = pagerAdapter

        fragmentProductDetailsBinding.apply {

            detailsImages.offscreenPageLimit = 1
            detailsImages.clipChildren = false
            detailsImages.clipToPadding = false

            detailsImages.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            val transformer = CompositePageTransformer()

            transformer.addTransformer(MarginPageTransformer(30))
            transformer.addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.90f + r * 0.14f
            }
            detailsImages.setPageTransformer(transformer)

        }


        fragmentProductDetailsBinding.addToCart.setOnClickListener {

            val lot = Lot(
                id = viewmodel.checkedDeviceInfo.value!!.id.toInt(),
                images = viewmodel.checkedDeviceInfo.value!!.images[0],
                price = viewmodel.checkedDeviceInfo.value!!.price,
                title = viewmodel.checkedDeviceInfo.value!!.title
            )

            viewmodel.addLotToCart(lot)
        }

        viewmodel.checkedDeviceInfo.observe(viewLifecycleOwner) { details ->
            fragmentProductDetailsBinding.apply {
                detailsTitle.text = details.title
                if (details.isFavorites != detailsIsFavorite.isChecked) {
                    detailsIsFavorite.toggle()
                }
                detailsRatingBar.rating = details.rating
                cpuDescription.text = details.cpu
                cameraDescription.text = details.camera
                ramDescription.text = details.ramCapacity
                romDescription.text = details.maxSdCapacity
                colorAdapter.setList(details.colors)
                romAdapter.setList(details.romCapacity)
                addToCart.text = "Add to cart \$${details.price}"

                pagerAdapter.setList(details.images)
            }
        }
        Toast.makeText(requireContext(),"  ", Toast.LENGTH_SHORT ).show()

    }

    override fun onStart() {
        super.onStart()
        viewmodel.downloadInfoCheckedDevice()
    }

}