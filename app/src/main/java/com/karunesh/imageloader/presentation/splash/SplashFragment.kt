package com.karunesh.imageloader.presentation.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.karunesh.imageloader.R
import com.karunesh.imageloader.databinding.FragmentSplashBinding
import com.karunesh.imageloader.presentation.SharedViewModel
import com.karunesh.imageloaderx.core.Loader.imageLoader
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private val viewModel: SharedViewModel by activityViewModels()

    private val showProgressText = object : CountDownTimer(MILLIS_IN_FUTURE, COUNTDOWN_INTERVAL) {
        override fun onTick(millisUntilFinished: Long) {
        }

        override fun onFinish() {
            binding.loadingMsg.text = resources.getString(R.string.almost_there)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_splash,
            container,
            false,
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getThumbnails()

        lifecycleScope.launch {
            viewModel.uriState.collect { uriList ->
                viewModel.imageLoader = context?.imageLoader()
                viewModel.imageLoader?.preDownloadIntoCache(uriList) { value ->
                    if (value) {
                        showProgressText.cancel()
                        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.errorState.collect { value ->
                if (value) {
                    showProgressText.cancel()
                    binding.loadingMsg.text = resources.getString(R.string.error)
                    binding.progress.visibility = View.GONE
                } else {
                    showProgressText.start()
                }
            }

        }

    }

    companion object {
        const val MILLIS_IN_FUTURE = 5000L
        const val COUNTDOWN_INTERVAL = 1000L
    }
}