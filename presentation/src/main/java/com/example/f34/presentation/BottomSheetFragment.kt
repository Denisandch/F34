package com.example.f34.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.f34.databinding.FragmentBottomSheetBinding
import com.example.f34.presentation.viewmodels.ApplicationViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var bottomSheetBinding: FragmentBottomSheetBinding
    private val viewmodel by viewModel<ApplicationViewModel>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bottomSheetBinding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return bottomSheetBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bottomSheetBinding.filterBrand.adapter = adapter

        initOnClickers()
        initObserves()

    }

    private fun initOnClickers() {
        bottomSheetBinding.toolBar.exit.setOnClickListener {
            findNavController().popBackStack()
        }

        bottomSheetBinding.toolBar.applyFilter.setOnClickListener {
            val brand =
                bottomSheetBinding
                    .filterBrand
                    .selectedItem
                    .toString()

            val lowPrice =
                bottomSheetBinding
                    .filterPrice.selectedItem
                    .toString()
                    .substringAfter("$")
                    .substringBefore(" ")
                    .toInt()
            Log.i(" ", "$lowPrice")

            val highPrice =
                bottomSheetBinding
                    .filterPrice
                    .selectedItem
                    .toString()
                    .substringAfterLast("$")
                    .toInt()
            Log.i(" ", "$highPrice")

            viewmodel.applyFilters(
                brand = brand,
                lowPrice = lowPrice,
                highPrice = highPrice)

            findNavController().popBackStack()
        }
    }

    private fun initObserves(){
        viewmodel.listOfBrands.observe(viewLifecycleOwner) {
            adapter.addAll(it)
        }
    }

}