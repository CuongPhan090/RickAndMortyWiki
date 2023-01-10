package com.example.rickandmortywiki.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.FragmentAllEpisodesBinding
import com.example.rickandmortywiki.epoxy.controller.EpisodeListEpoxyController
import com.example.rickandmortywiki.model.EpisodeUiModel
import com.example.rickandmortywiki.viewmodel.SharedViewModel
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AllEpisodesFragment : BaseFragment("All Episode") {

    private var _binding: FragmentAllEpisodesBinding? = null
    private val binding
        get() = _binding

    private val viewModel: SharedViewModel by viewModels()
    private val episodeListEpoxyController = EpisodeListEpoxyController(::onEpisodeClick)
    private lateinit var navView: NavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllEpisodesBinding.inflate(layoutInflater)
        navView = requireActivity().findViewById(R.id.nav_view)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.allEpisodeEpoxyRecyclerView?.setControllerAndBuildModels(episodeListEpoxyController)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allEpisodePagination.collectLatest { pagingData: PagingData<EpisodeUiModel> ->
                    episodeListEpoxyController.showShimmering = false
                    episodeListEpoxyController.submitData(pagingData)
                }
            }
        }
        navView.menu.findItem(R.id.allEpisodesFragment).isChecked = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        navView.menu.findItem(R.id.allEpisodesFragment).isChecked = false
        _binding = null
    }

    private fun onEpisodeClick(episodeNumber: Int) {
        findNavController().navigate(AllEpisodesFragmentDirections.actionAllEpisodesFragmentToEpisodeDetailBottomSheetFragment(episodeNumber))
    }
}
