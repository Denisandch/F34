package com.example.f34

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.domain.model.mainscreen.BestSellerDevice
import com.example.f34.adapters.BestSellerAdapter
import com.example.f34.adapters.BestSellerInterface
import com.example.f34.adapters.HotSalesAdapter
import com.example.f34.databinding.FragmentMainScreenBinding
import com.example.f34.viewmodels.ApplicationViewModel


class MainScreenFragment : Fragment(), BestSellerInterface {

    private val adapterBest = BestSellerAdapter(this)
    private val adapterHot = HotSalesAdapter()
    private val viewmodel: ApplicationViewModel by activityViewModels()
    private lateinit var fragmentMainScreenBinding: FragmentMainScreenBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("TAG", "onCreateView")
        fragmentMainScreenBinding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return fragmentMainScreenBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("TAG", "onViewCreated")
        fragmentMainScreenBinding.toolBar.mainScreenToolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.filter -> findNavController().navigate(R.id.action_explorer_to_bottomSheetFragment)
            }
            true
        }

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
}