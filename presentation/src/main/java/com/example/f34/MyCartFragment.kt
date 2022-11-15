package com.example.f34

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.f34.databinding.FragmentMyCartBinding


class MyCartFragment : Fragment() {

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
    }
}