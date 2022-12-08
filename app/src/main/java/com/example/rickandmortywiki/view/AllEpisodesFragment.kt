package com.example.rickandmortywiki.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.example.rickandmortywiki.databinding.FragmentAllEpisodesBinding
import com.example.rickandmortywiki.epoxy.uimodel.EpisodeListEpoxyController
import com.example.rickandmortywiki.model.EpisodeUiModel
import com.example.rickandmortywiki.viewmodel.SharedViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AllEpisodesFragment : Fragment() {

    private var _binding: FragmentAllEpisodesBinding? = null
    private val binding
        get() = _binding

    private val viewModel: SharedViewModel by viewModels()
    private val episodeListEpoxyController = EpisodeListEpoxyController(::onEpisodeClick)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllEpisodesBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.allEpisodeEpoxyRecyclerView?.setController(episodeListEpoxyController)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allEpisodePagination.collectLatest { pagingData: PagingData<EpisodeUiModel> ->
                episodeListEpoxyController.submitData(pagingData)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onEpisodeClick(episodeNumber: Int) {
        findNavController().navigate(AllEpisodesFragmentDirections.actionAllEpisodesFragmentToEpisodeDetailBottomSheetFragment(episodeNumber))
    }
}
