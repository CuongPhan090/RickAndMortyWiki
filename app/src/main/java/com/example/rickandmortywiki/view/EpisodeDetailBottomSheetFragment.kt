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
import androidx.navigation.fragment.navArgs
import com.example.rickandmortywiki.databinding.FragmentEpisodeDetailBinding
import com.example.rickandmortywiki.epoxy.uimodel.EpisodeDetailsEpoxyController
import com.example.rickandmortywiki.model.domain.Episode
import com.example.rickandmortywiki.viewmodel.SharedViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class EpisodeDetailBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentEpisodeDetailBinding? = null
    private val binding: FragmentEpisodeDetailBinding?
        get() = _binding

    private val viewModel: SharedViewModel by viewModels()

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
                    findNavController().navigate(EpisodeDetailBottomSheetFragmentDirections.actionEpisodeDetailBottomSheetFragmentToCharacterDetailFragment(id ?: -1))
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}