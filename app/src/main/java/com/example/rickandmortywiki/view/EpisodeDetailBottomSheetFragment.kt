package com.example.rickandmortywiki.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.rickandmortywiki.databinding.FragmentEpisodeDetailBinding
import com.example.rickandmortywiki.epoxy.controller.EpisodeDetailsEpoxyController
import com.example.rickandmortywiki.model.domain.Episode
import com.example.rickandmortywiki.repository.SharedRepository
import com.example.rickandmortywiki.viewmodel.SharedViewModel
import com.example.rickandmortywiki.viewmodel.SharedViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import java.lang.reflect.Modifier

class EpisodeDetailBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentEpisodeDetailBinding? = null

    @VisibleForTesting(otherwise = Modifier.PRIVATE)
    val binding: FragmentEpisodeDetailBinding?
        get() = _binding

    private val viewModel: SharedViewModel by viewModels { SharedViewModelFactory(SharedRepository()) }

    private val navArgs: EpisodeDetailBottomSheetFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEpisodeDetailBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.episode.collect {
                    bindData(it)
                }
            }
        }
        viewModel.fetchEpisode(navArgs.episodeId.toString())
    }

    private fun bindData(episode: Episode?) {
        binding?.let {
            it.episodeName.text = episode?.name
            it.episodeAirDay.text = episode?.airDate
            it.seasonEpisodeNumber.text = episode?.getFormattedSeason()
            it.charactersCarousel.setControllerAndBuildModels(
                EpisodeDetailsEpoxyController(episode?.characters) { id ->
                    onEpisodeDetailClick(id)
                }
            )
        }
    }

    @VisibleForTesting(otherwise = Modifier.PRIVATE)
    fun onEpisodeDetailClick(index: Int?) {
        val direction =
            EpisodeDetailBottomSheetFragmentDirections.actionEpisodeDetailBottomSheetFragmentToCharacterDetailFragment(
                index ?: -1
            )
        findNavController().navigate(directions = direction)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}