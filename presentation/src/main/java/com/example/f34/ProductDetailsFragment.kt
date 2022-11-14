package com.example.f34

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.f34.databinding.FragmentProductDetailsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class ProductDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolBar.cart.setOnClickListener{
            val botNav = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
            botNav?.selectedItemId = R.id.cart
            findNavController().popBackStack(R.id.productDetailsFragment,true)
        }
    }
}