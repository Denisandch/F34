package com.example.f34

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.f34.adapters.BestSellerAdapter
import com.example.f34.adapters.HotSalesAdapter
import com.example.f34.databinding.FragmentMainScreenBinding
import com.example.f34.viewmodels.MainScreenViewModel


class MainScreenFragment : Fragment() {

    private val adapterBest = BestSellerAdapter()
    private val adapterHot = HotSalesAdapter()
    private val viewmodel: MainScreenViewModel by activityViewModels()
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

        fragmentMainScreenBinding.hotSalesRecycler.adapter = adapterHot
        fragmentMainScreenBinding.bestSellerRecycler.adapter = adapterBest

        viewmodel.HOT.observe(viewLifecycleOwner) {
            adapterHot.submitList(it)
        }

        viewmodel.BEST.observe(viewLifecycleOwner) {
            adapterBest.submitList(it)
        }
    }

    override fun onStart() {
        super.onStart()
        viewmodel.download()
    }
}