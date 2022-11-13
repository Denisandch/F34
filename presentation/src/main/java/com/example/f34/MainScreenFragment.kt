package com.example.f34

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.f34.databinding.FragmentMainScreenBinding


class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolBar.mainScreenToolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.filter -> findNavController().navigate(R.id.action_mainScreenFragment_to_bottomSheetFragment)
            }
            true
        }


    }
}