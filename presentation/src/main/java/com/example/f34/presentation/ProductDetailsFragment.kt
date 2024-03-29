package com.example.f34.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.domain.model.cart.Lot
import com.example.f34.R
import com.example.f34.presentation.adapters.ColorAdapter
import com.example.f34.presentation.adapters.RomAdapter
import com.example.f34.presentation.adapters.ViewPagerImageAdapter
import com.example.f34.databinding.FragmentProductDetailsBinding
import com.example.f34.presentation.viewmodels.ApplicationViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

class ProductDetailsFragment : Fragment() {

    private val colorAdapter = ColorAdapter()
    private val romAdapter = RomAdapter()
    private val pagerAdapter = ViewPagerImageAdapter()

    private lateinit var fragmentProductDetailsBinding: FragmentProductDetailsBinding
    private val viewModel by viewModel<ApplicationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentProductDetailsBinding =
            FragmentProductDetailsBinding.inflate(inflater, container, false)
        return fragmentProductDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentProductDetailsBinding.recyclerColor.adapter = colorAdapter
        fragmentProductDetailsBinding.recycletCapacity.adapter = romAdapter
        fragmentProductDetailsBinding.detailsImages.adapter = pagerAdapter

        initOnClickers()
        viewPagerSetting()
        initObserves()
    }

    override fun onStart() {
        super.onStart()
        viewModel.downloadInfoCheckedDevice()
    }

    private fun initOnClickers() {
        fragmentProductDetailsBinding.toolBar.cart.setOnClickListener {
            val botNav = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
            findNavController().popBackStack(R.id.productDetailsFragment, true)
            botNav?.selectedItemId = R.id.cart
        }

        fragmentProductDetailsBinding.toolBar.exit.setOnClickListener {
            findNavController().popBackStack()
        }

        fragmentProductDetailsBinding.addToCart.setOnClickListener {
            try {
                val lot = Lot(
                    id = viewModel.checkedDeviceInfo.value!!.id.toInt(),
                    images = viewModel.checkedDeviceInfo.value!!.images[0],
                    price = viewModel.checkedDeviceInfo.value!!.price,
                    title = viewModel.checkedDeviceInfo.value!!.title
                )
                viewModel.addLotToCart(lot)
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    viewModel.connectionResult.value,
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }

    private fun initObserves() {
        viewModel.checkedDeviceInfo.observe(viewLifecycleOwner) { details ->
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
                addToCart.text = getString(R.string.add_to_cart, "\$${details.price}")
                pagerAdapter.setList(details.images)
            }
        }


        viewModel.connectionResult.observe(viewLifecycleOwner) {
            if (it != Constant.SUCCESS) {
                Toast.makeText(requireContext(), Constant.ERROR, Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    viewModel.downloadInfoCheckedDevice()
                }, 2000)
            }
        }
    }

    private fun viewPagerSetting() {
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
    }

}