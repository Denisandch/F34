package com.example.f34

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.domain.model.cart.Lot
import com.example.f34.adapters.CartAdapter
import com.example.f34.adapters.CartInterface
import com.example.f34.databinding.FragmentMyCartBinding
import com.example.f34.viewmodels.ApplicationViewModel


class MyCartFragment : Fragment(), CartInterface {

    private val adapterCart = CartAdapter(this)
    private val viewmodel: ApplicationViewModel by activityViewModels()
    private lateinit var fragmentMyCartBinding: FragmentMyCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentMyCartBinding = FragmentMyCartBinding.inflate(inflater, container, false)
        return fragmentMyCartBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentMyCartBinding.toolBar.exit.setOnClickListener {
            findNavController().popBackStack()
        }
        fragmentMyCartBinding.toolBar.location.setOnClickListener {
            Toast.makeText(requireContext(), "Temporary Empty", Toast.LENGTH_SHORT).show()
        }

        fragmentMyCartBinding.cartRecycler.adapter = adapterCart

        viewmodel.cartInfo.observe(viewLifecycleOwner) {
            adapterCart.submitList(it.basket)
            adapterCart.notifyDataSetChanged()

        }

        viewmodel.cartInfo.observe(viewLifecycleOwner) {
            fragmentMyCartBinding.cartDeliveryCost.text = it.deliveryString
        }

        viewmodel.totalPrice.observe(viewLifecycleOwner) {
            fragmentMyCartBinding.cartSummaryCost.text = "\$${it} us"
        }

    }

    override fun upCount(lot: Lot) {
        viewmodel.upCountOfLot(lot)
    }

    override fun downCount(lot: Lot) {
        viewmodel.downCountOfLot(lot)
    }

    override fun deleteFromCart(lot: Lot) {
        viewmodel.deleteLotFromCart(lot)
    }

}