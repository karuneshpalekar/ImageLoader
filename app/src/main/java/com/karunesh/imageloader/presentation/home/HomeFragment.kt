package com.karunesh.imageloader.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.karunesh.imageloader.R
import com.karunesh.imageloader.databinding.FragmentHomeBinding
import com.karunesh.imageloader.presentation.SharedViewModel
import com.karunesh.imageloader.presentation.home.adapter.HomeAdapter
import com.karunesh.imageloaderx.core.Loader.imageLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var homeAdapter: HomeAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false,
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = GridLayoutManager(context, 3)
        binding.homeRv.layoutManager = layoutManager
        homeAdapter = HomeAdapter(viewModel.imageLoader ?: requireContext().imageLoader())
        binding.homeRv.adapter = homeAdapter

        viewModel.getData()

        lifecycleScope.launch {
            viewModel.dataState.collect {
                homeAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            viewModel.errorState.collect { value ->
                if (value) {
                    with(binding) {
                        if (homeRv.visibility == View.VISIBLE)
                            homeRv.visibility = View.GONE

                        if (noData.visibility == View.GONE)
                            noData.visibility = View.VISIBLE
                    }
                } else {
                    with(binding) {
                        if (homeRv.visibility == View.GONE)
                            homeRv.visibility = View.VISIBLE

                        if (noData.visibility == View.VISIBLE)
                            noData.visibility = View.GONE
                    }
                }

            }

        }

    }


}