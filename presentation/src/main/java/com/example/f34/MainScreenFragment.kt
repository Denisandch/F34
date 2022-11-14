package com.example.f34

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.f34.databinding.FragmentMainScreenBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Toast.makeText(requireContext(), "создано", Toast.LENGTH_SHORT).show()
        binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.toolBar.mainScreenToolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.filter -> findNavController().navigate(R.id.action_explorer_to_bottomSheetFragment)
            }
            true
        }
        binding.imageFilterButton.setOnClickListener{
            findNavController().navigate(R.id.action_explorer_to_productDetailsFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(requireContext(), "уничтожено", Toast.LENGTH_SHORT).show()
    }
    override fun onResume() {
        super.onResume()
//        val botNav = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)
//        if(botNav?.visibility == View.GONE) botNav?.visibility = View.VISIBLE
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainScreenFragment()
    }
}